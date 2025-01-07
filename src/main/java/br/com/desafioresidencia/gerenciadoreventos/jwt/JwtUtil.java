package br.com.desafioresidencia.gerenciadoreventos.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    /**
     * Gera um token JWT com base na autenticação fornecida.
     * 
     * @param authentication Objeto de autenticação
     * @return Token JWT gerado
     */
    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName()) // Define o nome do usuário (ou admin) como "subject"
                .setIssuedAt(new Date()) // Data de emissão
                .setExpiration(new Date((new Date()).getTime() + expiration)) // Data de expiração
                .signWith(SignatureAlgorithm.HS512, secret) // Assina o token com a chave secreta
                .compact(); // Gera o token JWT em formato compacto
    }

    /**
     * Obtém o nome de usuário (ou admin) a partir do token JWT.
     * 
     * @param token Token JWT
     * @return Nome do usuário (ou admin)
     */
    public String getUserNameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret) // Define a chave secreta para validação
                .parseClaimsJws(token) // Analisa o token
                .getBody() // Obtém o corpo das Claims
                .getSubject(); // Retorna o "subject" (nome do usuário/admin)
    }

    /**
     * Valida o token JWT fornecido.
     * 
     * @param token Token JWT
     * @return true se o token for válido, false caso contrário
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);
            return true; // Token válido
        } catch (JwtException e) {
            logger.error("Token JWT inválido: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("Token JWT está vazio ou inválido: {}", e.getMessage());
        }

        return false; // Token inválido
    }
}
