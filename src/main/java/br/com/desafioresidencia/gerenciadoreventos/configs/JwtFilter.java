package br.com.desafioresidencia.gerenciadoreventos.configs;

import br.com.desafioresidencia.gerenciadoreventos.security.utils.JwtUtil;
import java.io.IOException; // Correção da importação
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Component // Indica que esta classe é um componente Spring
public class JwtFilter extends OncePerRequestFilter {

    public static final Logger logger = getLogger(JwtFilter.class.getName()); // Logger para registrar informações

    private final JwtUtil jwtUtil; // Utilitário para manipulação de tokens JWT
    private final CustomUserDetailService customUserDetailService; // Serviço personalizado para carregar detalhes do usuário

    private static final List<String> EXCLUDED_URLS = Arrays.asList(
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    ); // Lista de URLs excluídas do filtro

    // Construtor que inicializa os atributos jwtUtil e customUserDetailService
    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailService customUserDetailService) {
        this.jwtUtil = jwtUtil;
        this.customUserDetailService = customUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws
            ServletException, IOException { // Correção do parâmetro filterChain
        String authorizationHeader = request.getHeader("Authorization"); // Verifica o cabeçalho de autorização para um token JWT
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extrai o token JWT do cabeçalho
            if (jwtUtil.verificarToken(token)) { // Verifica se o token é válido
                String email = jwtUtil.extrairEmail(token); // Extrai o email do token
                if (email != null) {
                    logger.info("Token válido para o email: {}", email); // Registra o email associado ao token válido
                    customUserDetailService.loadUserByUsername(email); // Carrega os detalhes do usuário
                }
            }
        }

        filterChain.doFilter(request, response); // Continua a cadeia de filtros
    }
}
