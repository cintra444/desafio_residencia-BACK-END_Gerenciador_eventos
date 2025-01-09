package br.com.desafioresidencia.gerenciadoreventos.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.desafioresidencia.gerenciadoreventos.security.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    
    @Override
    protected void doFilterInternal(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extrairToken(request);

        if (token != null && jwtUtil.verificarToken(token)) {
            String email = jwtUtil.extrairEmail(token);

            // Aqui pode-se buscar o usuário no banco, se necessário
            if (email != null) {
                SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(email));
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extrairToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Remover o "Bearer " do token
        }

        return null;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
