package br.com.desafioresidencia.gerenciadoreventos.security.utils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.desafioresidencia.gerenciadoreventos.security.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Component
public class JwtUtil {

   @Autowired
   private JwtConfig jwtConfig;
   
   @Value("${jwt.secret}")
   private String secretKey;

    private static final long EXPIRATION_TIME = 864_000_000; // 10 dias

    
    public String gerarToken(String email) {
    	
    	 if (jwtConfig.getSecretKey() == null || jwtConfig.getSecretKey().isEmpty()) {
    	        throw new IllegalArgumentException("A chave secreta do JWT não pode ser vazia");
    	    }
    	 	byte[] keyBytes = jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8);
    	 	SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
    	 	
    	    return Jwts.builder().setSubject(email).setIssuedAt(new Date())
    	            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
    	            .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecretKey()).compact();
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
        try {
            Claims claims = extrairClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return false; // Retorna falso se o token estiver expirado
        }
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
