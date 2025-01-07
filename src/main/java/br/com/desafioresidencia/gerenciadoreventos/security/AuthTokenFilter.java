package br.com.desafioresidencia.gerenciadoreventos.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.desafioresidencia.gerenciadoreventos.jwt.JwtUtil;
import br.com.desafioresidencia.gerenciadoreventos.services.AdminDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtils;

    @Autowired
    private AdminDetailsServiceImpl adminDetailsService; // Serviço de detalhes do administrador

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Extrair o token JWT do cabeçalho da requisição
            String jwt = extractJwtFromRequest(request);

            // Validar o token JWT
            if (jwt != null && jwtUtils.validateToken(jwt)) {
                // Obter o nome do administrador a partir do token
                String username = jwtUtils.getUserNameFromToken(jwt);

                // Carregar os detalhes do administrador
                UserDetails userDetails = adminDetailsService.loadUserByUsername(username);

                // Criar o objeto de autenticação para o contexto de segurança
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Definir a autenticação no contexto de segurança
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Erro ao autenticar o administrador: {}", e.getMessage());
        }

        // Continuar o processamento da requisição
        filterChain.doFilter(request, response);
    }

    /**
     * Extrai o token JWT do cabeçalho da requisição.
     *
     * @param request A requisição HTTP
     * @return O token JWT ou null se não estiver presente
     */
    private String extractJwtFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Remove o prefixo "Bearer "
        }

        return null;
    }
}
