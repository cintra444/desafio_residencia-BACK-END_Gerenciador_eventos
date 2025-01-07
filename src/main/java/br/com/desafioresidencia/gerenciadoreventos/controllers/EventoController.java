package br.com.desafioresidencia.gerenciadoreventos.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafioresidencia.gerenciadoreventos.dtos.EventoRequestDTO;
import br.com.desafioresidencia.gerenciadoreventos.dtos.EventoUpdateDTO;
import br.com.desafioresidencia.gerenciadoreventos.models.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.models.Evento;
import br.com.desafioresidencia.gerenciadoreventos.repositories.AdminRepository;
import br.com.desafioresidencia.gerenciadoreventos.repositories.EventoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/eventos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EventoController {

	@Autowired
	private EventoRepository eventoRepository;

	@Autowired
	private AdminRepository adminRepository;

	@GetMapping
	@Operation(summary = "Listagem de todos os eventos", description = "Retorna todos os eventos.")
	@ApiResponse(responseCode = "200", description = "Eventos listados com sucesso.")
	public ResponseEntity<List<Evento>> listarTodosEventos() {
		return ResponseEntity.ok(eventoRepository.findAll());
	}

	@GetMapping("/admin/{adminId}")
	@Operation(summary = "Listagem de eventos por administrador", description = "Retorna todos os eventos associados ao administrador.")
	@ApiResponse(responseCode = "200", description = "Eventos listados com sucesso.")
	public ResponseEntity<List<Evento>> listarEventosPorAdmin(
			@PathVariable Long adminId) {
		List<Evento> eventos = eventoRepository.findByAdminId(adminId);

		if (eventos.isEmpty()) {
			return ResponseEntity.noContent().build(); // status 204
		}

		return ResponseEntity.ok(eventos);
	}

	@PostMapping("/admin/{adminId}")
	@Operation(summary = "Cadastro de evento", description = "Cadastra um evento associado ao administrador.")
	@ApiResponse(responseCode = "201", description = "Evento cadastrado com sucesso.")
	public ResponseEntity<?> cadastrarEvento(@PathVariable Long adminId,
			@RequestBody EventoRequestDTO eventoRequest) {
		Administrador admin = adminRepository.findById(adminId)
				.orElseThrow(() -> new RuntimeException(
						"Erro: Administrador nao encontrado!"));

		Evento evento = new Evento(null, eventoRequest.getNome(),
				eventoRequest.getData(), eventoRequest.getLocalizacao(),
				eventoRequest.getImagem(), admin);
		eventoRepository.save(evento);

		return ResponseEntity.status(201)
				.body("Evento cadastrado com sucesso!");
	}

	@PutMapping("/{eventoId}")
	@Operation(summary = "Atualização de evento", description = "Atualiza a data ou localização de um evento.")
	@ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso.")
	public ResponseEntity<?> atualizarEvento(@PathVariable Long eventoId,
			@RequestBody EventoUpdateDTO eventoUpdate) {
		Evento evento = eventoRepository.findById(eventoId).orElseThrow(
				() -> new RuntimeException("Erro: Evento não encontrado!"));

		if (eventoUpdate.getData() != null) {
			evento.setData(eventoUpdate.getData());
		}
		if (eventoUpdate.getLocalizacao() != null) {
			evento.setLocalizacao(eventoUpdate.getLocalizacao());
		}

		eventoRepository.save(evento);

		return ResponseEntity.ok("Evento atualizado com sucesso!");
	}

	@DeleteMapping("/{eventoId}")
	@Operation(summary = "Exclusão de evento", description = "Exclui um evento usando o eventoId.")
	@ApiResponse(responseCode = "200", description = "Evento excluído com sucesso.")
	public ResponseEntity<?> excluirEvento(@PathVariable Long eventoId) {
		Evento evento = eventoRepository.findById(eventoId)
				.orElseThrow(() -> new RuntimeException("Evento não encontrado"));
		
		eventoRepository.delete(evento);
		return ResponseEntity.ok("Evento excluído com sucesso!");
	}

}
