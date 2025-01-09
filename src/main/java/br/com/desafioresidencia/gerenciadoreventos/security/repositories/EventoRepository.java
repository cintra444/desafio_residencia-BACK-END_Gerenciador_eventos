package br.com.desafioresidencia.gerenciadoreventos.security.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Evento;

@Repository("evento")
public interface EventoRepository extends JpaRepository<Evento, Long> {
	List<Evento> findByAdministradorId(Long administradorId);

}
