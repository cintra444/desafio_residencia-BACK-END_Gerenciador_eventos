package br.com.desafioresidencia.gerenciadoreventos.security.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Evento;
import br.com.desafioresidencia.gerenciadoreventos.security.services.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EventoController {

	@Autowired
	private EventoService eventoService;

	@GetMapping("listar/{adminId}")
	@Operation(summary = "Listagem de todos os eventos", description = "Retorna todos os eventos.")
	@ApiResponse(responseCode = "200", description = "Eventos listados com sucesso.")
	public List<Evento> listarTodosEventos(@PathVariable Long adminId) {
		return eventoService.listarTodosEventos(adminId);
		
	}

//	@GetMapping("/admin/{adminId}")
//	@Operation(summary = "Listagem de eventos por administrador", description = "Retorna todos os eventos associados ao administrador.")
//	@ApiResponse(responseCode = "200", description = "Eventos listados com sucesso.")
//	public ResponseEntity<List<Evento>> listarEventosPorAdmin(
//			@PathVariable Long adminId) {
//		List<Evento> eventos = eventoRepository.findByAdminId(adminId);
//
//		if (eventos.isEmpty()) {
//			return ResponseEntity.noContent().build(); // status 204
//		}
//
//		return ResponseEntity.ok(eventos);
//	}

	@PostMapping("/cadastro")
	@Operation(summary = "Cadastro de evento", description = "Cadastra um evento associado ao administrador.")
	@ApiResponse(responseCode = "201", description = "Evento cadastrado com sucesso.")
	public Evento cadastrarEvento(@RequestBody Evento evento) {
		return eventoService.cadastrarEvento(evento);	
	}

	@PutMapping("/atualizar/{eventoId}")
	@Operation(summary = "Atualização de evento", description = "Atualiza a data ou localização de um evento.")
	@ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso.")
	public Evento atualizarEvento(@PathVariable Long eventoId, @RequestBody LocalDate data, @RequestBody String localizacao) {
		return eventoService.atualizarEvento(eventoId, data, localizacao);
		
	}

	@DeleteMapping("/excluir/{eventoId}")
	@Operation(summary = "Exclusão de evento", description = "Exclui um evento usando o eventoId.")
	@ApiResponse(responseCode = "200", description = "Evento excluído com sucesso.")
	public void excluirEvento(@PathVariable Long eventoId) {
		eventoService.excluirEvento(eventoId);		
	}
	
//	@PostMapping("/{eventoId}/imagem")
//	@Operation(summary = "Inserir imagem", description = "Inserir uma imagem em um evento.")
//	@ApiResponse(responseCode = "200", description = "Imagem inserida com sucesso.")
//	public ResponseEntity<String> uploadImagem(@PathVariable Long eventoId,
//			@RequestParam("file") MultipartFile file) {
//		try {
//			// Salva a imagem e obtém a URL
//			String urlImagem = eventoService.salvarImagem(file);
//
//			// Associa a URL da imagem ao evento
//			eventoService.associarImagemAoEvento(eventoId, urlImagem);
//
//			return ResponseEntity
//					.ok("Imagem carregada com sucesso. URL: " + urlImagem);
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//					.body("Erro ao fazer upload da imagem: " + e.getMessage());
//		}
//	}
}