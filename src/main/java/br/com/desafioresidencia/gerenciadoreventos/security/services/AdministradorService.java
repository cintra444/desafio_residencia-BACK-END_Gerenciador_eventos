package br.com.desafioresidencia.gerenciadoreventos.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Usuario;
import br.com.desafioresidencia.gerenciadoreventos.security.exceptions.EmailAlreadyExistsException;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.UsuarioRepository;

@Service
public class AdministradorService {

    @Autowired
    private UsuarioRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Usuario cadastrarAdministrador(Usuario admin) {
        // Validação de email duplicado
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new EmailAlreadyExistsException("Administrador com esse email já cadastrado.");
        }

        // Validação de senha
        if (admin.getSenha() == null || admin.getSenha().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser nula ou vazia.");
        }

        // Criptografar senha
        admin.setSenha(passwordEncoder.encode(admin.getSenha()));

        // Salvar administrador
        try {
            return adminRepository.save(admin);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar administrador: " + e.getMessage());
        }
    }
}
