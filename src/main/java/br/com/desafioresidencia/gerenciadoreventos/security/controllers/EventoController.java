package br.com.desafioresidencia.gerenciadoreventos.security.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.desafioresidencia.gerenciadoreventos.security.dtos.EventoDTO;
import br.com.desafioresidencia.gerenciadoreventos.security.entities.Evento;
import br.com.desafioresidencia.gerenciadoreventos.security.services.EventoService;
import br.com.desafioresidencia.gerenciadoreventos.security.services.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/criar")
    @Operation(
        summary = "Criar um evento",
        description = "Cria um evento associado a um administrador e opcionalmente carrega uma imagem.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")
        }
    )
    public EventoDTO criarEvento(
            @RequestParam String nome,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam String localizacao,
            @RequestParam Long adminId,
            @RequestParam(required = false) MultipartFile image) throws Exception {

        String imagePath = null;
        if (image != null) {
            imagePath = fileUploadService.uploadImage(image);
        }

        Evento evento = eventoService.criarEvento(nome, data, localizacao, adminId, imagePath);
        return new EventoDTO(evento);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obter evento por ID",
        description = "Retorna o evento associado ao ID informado.",
        responses = {@ApiResponse(responseCode = "200", description = "Evento obtido com sucesso.")}
    )
    public EventoDTO obterEvento(@PathVariable Long id) {
        Evento evento = eventoService.obterEvento(id);
        return new EventoDTO(evento);
    }

    @GetMapping("/listar/{adminId}")
    @Operation(
        summary = "Listar eventos",
        description = "Retorna todos os eventos associados a um administrador.",
        responses = {@ApiResponse(responseCode = "200", description = "Eventos listados com sucesso.")}
    )
    public List<EventoDTO> listarTodosEventos(@PathVariable Long adminId) {
        return eventoService.listarTodosEventos(adminId).stream()
                .map(EventoDTO::new)
                .toList();
    }

    @PutMapping("/atualizar/{eventoId}")
    @Operation(
        summary = "Atualizar evento",
        description = "Atualiza a data e/ou localização de um evento.",
        responses = {@ApiResponse(responseCode = "200", description = "Evento atualizado com sucesso.")}
    )
    public EventoDTO atualizarEvento(
            @PathVariable Long eventoId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) String localizacao) {

        Evento evento = eventoService.atualizarEvento(eventoId, data, localizacao);
        return new EventoDTO(evento);
    }

    @DeleteMapping("/excluir/{eventoId}")
    @Operation(
        summary = "Excluir evento",
        description = "Exclui um evento pelo ID.",
        responses = {@ApiResponse(responseCode = "200", description = "Evento excluído com sucesso.")}
    )
    public void excluirEvento(@PathVariable Long eventoId) {
        eventoService.excluirEvento(eventoId);
    }
}
