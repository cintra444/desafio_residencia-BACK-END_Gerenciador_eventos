package br.com.desafioresidencia.gerenciadoreventos.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafioresidencia.gerenciadoreventos.security.services.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api/administradores")
public class LoginController {

	@Autowired
	private LoginService loginService;	
	

	@PostMapping("/login")
	@Operation(summary = "Login do administrador", description = "Realiza o login do administrador e retorna um token JWT.")
	@ApiResponse(responseCode = "200", description = "Login realizado com sucesso.")
	public String login(@RequestParam String email, @RequestParam String senha) {
		return loginService.login(email, senha); //retorna aqui o token
	}
}
