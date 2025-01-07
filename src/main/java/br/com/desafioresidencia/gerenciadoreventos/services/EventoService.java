package br.com.desafioresidencia.gerenciadoreventos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafioresidencia.gerenciadoreventos.dtos.EventoRequestDTO;
import br.com.desafioresidencia.gerenciadoreventos.models.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.models.Evento;
import br.com.desafioresidencia.gerenciadoreventos.repositories.AdminRepository;
import br.com.desafioresidencia.gerenciadoreventos.repositories.EventoRepository;

@Service
public class EventoService {

	@Autowired
	EventoRepository eventoRepository;

	@Autowired
	AdminRepository adminRepository;

	// listar eventos
	public List<Evento> listarTodosEventos() {
		return eventoRepository.findAll();
	}

	public List<Evento> listarEventosPorAdmin(Long adminId) {
		return eventoRepository.findByAdminId(adminId);
	}

	// cadastrar evento
	public Evento cadastrarEvento(Long adminId, EventoRequestDTO eventoDTO) {
		Administrador admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new RuntimeException(
						"Erro: Administrador não encontrado!"));

		Evento evento = new Evento(null, eventoDTO.getNome(),
				eventoDTO.getData(), eventoDTO.getLocalizacao(),
				eventoDTO.getImagem(), admin);
		return eventoRepository.save(evento);
	}

	public Evento atualizarEvento(Long eventoId, EventoRequestDTO eventoDTO) {
		Evento evento = eventoRepository.findById(eventoId).orElseThrow(
				() -> new RuntimeException("Erro: Evento não encontrado"));

		if (eventoDTO.getNome() != null) {
			evento.setNome((eventoDTO.getNome()));
		}
		if (eventoDTO.getData() !=null) {
			evento.setData(eventoDTO.getData());
		}
		if (eventoDTO.getLocalizacao() != null) {
			evento.setLocalizacao(eventoDTO.getLocalizacao());
		}
		
			return eventoRepository.save(evento);
	}

	public void deletarEvento(Long eventoId) {
		Evento evento = eventoRepository.findById(eventoId)
				.orElseThrow(() -> new RuntimeException("Erro: Evento não encontrado!"));
		eventoRepository.delete(evento);
	}

}