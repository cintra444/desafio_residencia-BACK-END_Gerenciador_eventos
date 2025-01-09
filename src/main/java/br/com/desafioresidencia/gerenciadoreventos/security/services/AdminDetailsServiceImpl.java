package br.com.desafioresidencia.gerenciadoreventos.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.AdministradorRepository;

@Service
public class AdminDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdministradorRepository adminRepository;
    
    @Autowired
	private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Administrador admin = adminRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Administrador não encontrado com o email: " + username));
        
        return User.builder()
                .username(admin.getEmail())
                .password(passwordEncoder.encode(admin.getSenha()))
                .roles("ADMIN")
                .build();
                
    }
}
