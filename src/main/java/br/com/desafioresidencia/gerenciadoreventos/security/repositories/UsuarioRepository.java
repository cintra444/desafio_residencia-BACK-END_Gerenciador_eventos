package br.com.desafioresidencia.gerenciadoreventos.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

   boolean existsByEmail(String email);
}
