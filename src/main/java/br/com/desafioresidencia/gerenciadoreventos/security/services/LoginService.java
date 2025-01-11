package br.com.desafioresidencia.gerenciadoreventos.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.security.exceptions.AuthenticationException;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.AdministradorRepository;
import br.com.desafioresidencia.gerenciadoreventos.security.utils.JwtUtil;

@Service
public class LoginService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String login(String email, String senha) {
        Optional<Administrador> administradorOpt = administradorRepository.findByEmail(email);

        if (administradorOpt.isPresent()) {
            Administrador administrador = administradorOpt.get();

            if (passwordEncoder.matches(senha, administrador.getSenha())) {
                return jwtUtil.gerarToken(email);
            }
        }

        throw new AuthenticationException("error.authentication.invalid", messageSource);
    }
}
