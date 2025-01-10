package br.com.desafioresidencia.gerenciadoreventos.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.desafioresidencia.gerenciadoreventos.security.dtos.AdministradorDTO;
import br.com.desafioresidencia.gerenciadoreventos.security.entities.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.security.services.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/administrador")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdministradorController {

	@Autowired
	private AdministradorService administradorService;

	@PostMapping("/cadastro")
	@Operation(summary = "Cadastra um administrador", description = "Recebe os dados de um administrador e retorna as informações do administrador cadastrado (sem expor a senha).", responses = {
			@ApiResponse(responseCode = "201", description = "Administrador cadastrado com sucesso.", content = @Content(schema = @Schema(implementation = AdministradorDTO.class))),
			@ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos.")})
	public AdministradorDTO cadastrarAdministrador(
			@Valid @RequestBody Administrador admin) {

		if (admin.getSenha() == null || admin.getSenha().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"A senha não pode ser nula ou vazia");
		}

		Administrador administradorSalvo = administradorService
				.cadastrarAdministrador(admin);
		return new AdministradorDTO(administradorSalvo);
	}
}
