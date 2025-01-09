package br.com.desafioresidencia.gerenciadoreventos.security.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Evento;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.EventoRepository;

@Service
public class EventoService {

    @Autowired
    EventoRepository eventoRepository;

    public List<Evento> listarTodosEventos(Long administradorId) {
		return eventoRepository.findByAdministradorId(administradorId);
	}
    
    public Evento cadastrarEvento(Evento evento) {
		return eventoRepository.save(evento);
    }
    
    public Evento atualizarEvento(Long eventoId, LocalDate data, String localizacao) {
		Optional<Evento> evento = eventoRepository.findById(eventoId);
		if(evento.isPresent()) {
			Evento e = evento.get();
			e.setData(data);
			e.setLocalizacao(localizacao);
			return eventoRepository.save(e);
		}
		return null;
	}
    
    public void excluirEvento(Long eventoId) {
		eventoRepository.deleteById(eventoId);
	}
    
}
