package br.com.desafioresidencia.gerenciadoreventos.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.security.services.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdministradorController {

	@Autowired
	AdministradorService administradorService;

	@PostMapping("/cadastro")
	@Operation(summary = "Cadastra um administrador", description = "Retorna o administrador cadastrado")
	@ApiResponse(responseCode = "201", description = "Retorna o administrador cadastrado")
	public ResponseEntity<Administrador> cadastrarAdministrador(
			@Valid @RequestBody Administrador admin) {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(administradorService.cadastrarAdministrador(admin));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(
			ConstraintViolationException ex) {
		return ResponseEntity.badRequest()
				.body("Erro de validação: " + ex.getMessage());
	}

}
