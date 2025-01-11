package br.com.desafioresidencia.gerenciadoreventos.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.Valid;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

	@Value("${jwt.secret}")
    private String secretKey;

    public String getSecretKey() {
        return secretKey;
    }
    public void setSecretKey(String secretKey) {
    	this.secretKey = secretKey;
    }
}
