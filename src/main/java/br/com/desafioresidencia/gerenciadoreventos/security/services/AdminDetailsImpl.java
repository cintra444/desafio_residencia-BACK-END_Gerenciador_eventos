package br.com.desafioresidencia.gerenciadoreventos.security.services;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Administrador;


public class AdminDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

   
 
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public AdminDetailsImpl( String email, String password,
                            Collection<? extends GrantedAuthority> authorities) {
        
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static AdminDetailsImpl build(Administrador admin) {
       
    	SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        
        return new AdminDetailsImpl(
                admin.getEmail(),
                admin.getSenha(),
				List.of(authority));
    }

    

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    //verificação email com o nome de usuario
    
    @Override
    public String getUsername() {
        return email; 
        }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    
}
