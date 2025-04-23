package br.com.desafioresidencia.gerenciadoreventos.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Usuario;
import br.com.desafioresidencia.gerenciadoreventos.security.exceptions.AuthenticationException;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.UsuarioRepository;
import br.com.desafioresidencia.gerenciadoreventos.security.utils.JwtUtil;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository administradorRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String login(String email, String senha) {
       Usuario administrador = administradorRepository.findByEmail(email)
    		   .orElseThrow(() -> new AuthenticationException("error.authentication.invalid", messageSource));

       if (!passwordEncoder.matches(senha, administrador.getSenha())) {
           throw new AuthenticationException("error.authentication.invalid", messageSource);
       }

       return jwtUtil.gerarToken(email);
   }
}