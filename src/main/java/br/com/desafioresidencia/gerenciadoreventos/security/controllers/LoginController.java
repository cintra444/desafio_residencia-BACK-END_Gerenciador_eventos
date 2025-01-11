package br.com.desafioresidencia.gerenciadoreventos.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafioresidencia.gerenciadoreventos.security.exceptions.AuthenticationException;
import br.com.desafioresidencia.gerenciadoreventos.security.services.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api/administrador")
public class LoginController {

	@Autowired
	private LoginService loginService;	
	

	@PostMapping("/login")
	@Operation(summary = "Login do administrador", description = "Realiza o login do administrador e retorna um token JWT.")
	@ApiResponse(responseCode = "200", description = "Login realizado com sucesso.")
	public ResponseEntity<String> login(@RequestParam String email, @RequestParam String senha) {
		try {
			 String token = loginService.login(email, senha);
		        return ResponseEntity.ok(token); 
		} catch (AuthenticationException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
	    }
	}
}
