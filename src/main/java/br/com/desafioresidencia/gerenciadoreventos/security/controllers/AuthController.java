package br.com.desafioresidencia.gerenciadoreventos.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.desafioresidencia.gerenciadoreventos.security.dtos.JwtResponseDTO;
import br.com.desafioresidencia.gerenciadoreventos.security.dtos.LoginRequestDTO;
import br.com.desafioresidencia.gerenciadoreventos.security.dtos.SignupRequestDTO;
import br.com.desafioresidencia.gerenciadoreventos.security.entities.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.security.jwt.JwtUtils;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.AdministradorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AdministradorRepository adminRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtil;

	@PostMapping("/login")
	@Operation(summary = "Login do administrador", description = "Realiza o login do administrador e retorna um token JWT.")
	@ApiResponse(responseCode = "200", description = "Login realizado com sucesso.")
	public ResponseEntity<?> authenticateAdmin(
			@RequestBody LoginRequestDTO loginRequest) {
		try {
			// Tentativa de autenticação
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(
							loginRequest.getEmail(), loginRequest.getSenha()));

			// Geração do token JWT
			String token = jwtUtil.generateToken(authentication);

			return ResponseEntity.ok(new JwtResponseDTO(token,
					"Login realizado com sucesso!", "Bearer"));

		} catch (org.springframework.security.authentication.BadCredentialsException e) {
			return ResponseEntity.status(401)
					.body("Erro: Credenciais inválidas.");
		} catch (Exception e) {
			return ResponseEntity.status(500)
					.body("Erro interno no servidor: " + e.getMessage());
		}
	}

	// Endpoint de Logout
	@PostMapping("/logout")
	@Operation(summary = "Logout do administrador", description = "Realiza o logout do administrador.")
	@ApiResponse(responseCode = "200", description = "Logout realizado com sucesso.")
	public ResponseEntity<Void> logout() {
		SecurityContextHolder.getContext().setAuthentication(null); // Limpa o
																	// contexto
																	// de
																	// segurança
		return ResponseEntity.noContent().build(); // Resposta sem conteúdo
	}

	// Endpoint de Registro
	@PostMapping("/register")
	@Operation(summary = "Cadastro do administrador", description = "Realiza o cadastro do administrador.")
	@ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso.")
	public ResponseEntity<?> registerAdmin(
			@Valid @RequestBody SignupRequestDTO signUpRequest) {
		// Verifica se o email já está cadastrado
		if (adminRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest()
					.body("Erro: Email já utilizado!");
		}

		// Criptografa a senha antes de salvar
		String encodedPassword = encoder.encode(signUpRequest.getSenha());

		// Cria o administrador com a role padrão 'ROLE_ADMIN'
		Administrador admin = new Administrador(null, signUpRequest.getNome(),
				signUpRequest.getEmail(), encodedPassword, "ROLE_ADMIN");
		adminRepository.save(admin);

		return ResponseEntity.ok("Administrador cadastrado com sucesso!");
	}
}
