package br.com.desafioresidencia.gerenciadoreventos.security.jwt;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	private static final long EXPIRATION_TIME = 864_000_000; // 10 dias
	
	public String generarToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}
	
	public Claims extrairClaims(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public String extrairEmail(String token) {
		return extrairClaims(token).getSubject();
	}
	
	public boolean verificarToken(String token) {
		return !extrairClaims(token).getExpiration().before(new Date());
	}
	
	public boolean autenticarToken(String token, String email) {
		return email.equals(extrairEmail(token)) && verificarToken(token);
	}
}

   