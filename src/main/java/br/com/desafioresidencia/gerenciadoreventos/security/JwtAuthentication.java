package br.com.desafioresidencia.gerenciadoreventos.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;


public class JwtAuthentication extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private final String email;

    
    public JwtAuthentication(String email) {
        super(null);
        this.email = email;
        setAuthenticated(true); 
    }

    
    @Override
    public Object getCredentials() {
        return null;
    }

    
    @Override
    public Object getPrincipal() {
        return email;
    }
}
