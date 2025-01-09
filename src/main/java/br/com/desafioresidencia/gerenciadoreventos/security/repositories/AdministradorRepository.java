package br.com.desafioresidencia.gerenciadoreventos.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Administrador;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByEmail(String email);

   boolean existsByEmail(String email);
}
