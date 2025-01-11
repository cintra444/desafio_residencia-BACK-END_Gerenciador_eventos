package br.com.desafioresidencia.gerenciadoreventos.security.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.desafioresidencia.gerenciadoreventos.security.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {

   @Autowired
   private JwtConfig jwtConfig;

    private static final long EXPIRATION_TIME = 864_000_000; // 10 dias

    // Gera o token JWT com o e-mail do usuário
    public String gerarToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecretKey())
                .compact();
    }

    // Extrai as claims (informações) do token JWT
    public Claims extrairClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(jwtConfig.getSecretKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expirado.", e);
        } catch (Exception e) {
            throw new RuntimeException("Token inválido.", e);
        }
    }

    // Extrai o e-mail do token JWT
    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

    // Verifica se o token ainda é válido (não expirou)
    public boolean verificarToken(String token) {
        return !extrairClaims(token).getExpiration().before(new Date());
    }

    // Autentica o token verificando o e-mail e a validade
    public boolean autenticarToken(String token, String email) {
        return email.equals(extrairEmail(token)) && verificarToken(token);
    }

    // Extrai o token JWT do cabeçalho Authorization da requisição HTTP
    public String extrairToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Retorna o token sem o prefixo "Bearer "
        }
        return null; // Retorna null se o token não for encontrado
    }
}
