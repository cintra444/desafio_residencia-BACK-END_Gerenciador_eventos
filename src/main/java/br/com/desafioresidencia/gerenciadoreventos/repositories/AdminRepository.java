package br.com.desafioresidencia.gerenciadoreventos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.desafioresidencia.gerenciadoreventos.models.Administrador;

public interface AdminRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByEmail(String email);
}
