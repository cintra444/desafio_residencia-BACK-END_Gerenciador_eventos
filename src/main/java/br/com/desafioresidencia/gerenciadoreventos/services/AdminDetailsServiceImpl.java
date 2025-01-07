package br.com.desafioresidencia.gerenciadoreventos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafioresidencia.gerenciadoreventos.models.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.repositories.AdminRepository;

@Service
public class AdminDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Administrador admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Administrador não encontrado com o email: " + email));

        return AdminDetailsImpl.build(admin);
    }
}
