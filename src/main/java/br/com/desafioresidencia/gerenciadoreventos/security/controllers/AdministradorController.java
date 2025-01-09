package br.com.desafioresidencia.gerenciadoreventos.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.security.services.AdministradorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/administrador")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdministradorController {

	@Autowired
	AdministradorService administradorService;

	@PostMapping("/cadastro")
	@Operation(summary = "Cadastra um administrador", description = "Retorna o administrador cadastrado")
	@ApiResponse(responseCode = "201", description = "Retorna o administrador cadastrado")
	public Administrador cadastrarAdministrador(@RequestBody Administrador admin) {

		return administradorService.cadastrarAdministrador(admin);
		
	}

}
