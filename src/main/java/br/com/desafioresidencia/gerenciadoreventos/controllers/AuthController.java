package br.com.desafioresidencia.gerenciadoreventos.controllers;

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

import br.com.desafioresidencia.gerenciadoreventos.dtos.JwtResponseDTO;
import br.com.desafioresidencia.gerenciadoreventos.dtos.LoginRequestDTO;
import br.com.desafioresidencia.gerenciadoreventos.dtos.SignupRequestDTO;
import br.com.desafioresidencia.gerenciadoreventos.jwt.JwtUtil;
import br.com.desafioresidencia.gerenciadoreventos.models.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.repositories.AdminRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    @Operation(summary = "Login do administrador", description = "Realiza o login do administrador e retorna um token JWT.")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso.")
    public ResponseEntity<?> authenticateAdmin(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha())
            );

            // Gera o token JWT
            String token = jwtUtil.generateToken(authentication);

            return ResponseEntity.ok(new JwtResponseDTO(token, "Login realizado com sucesso!", token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Erro: Credenciais inválidas.");
        }
    }

    // Endpoint de Logout
    @PostMapping("/logout")
    @Operation(summary = "Logout do administrador", description = "Realiza o logout do administrador.")
    @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso.")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return ResponseEntity.ok("Logout realizado com sucesso!");
    }

    // Endpoint de Registro
    @PostMapping("/register")
    @Operation(summary = "Cadastro do administrador", description = "Realiza o cadastro do administrador.")
    @ApiResponse(responseCode = "200", description = "Cadastro realizado com sucesso.")
    public ResponseEntity<?> registerAdmin(@RequestBody SignupRequestDTO signUpRequest) {
        // Verifica se o email ja foi utilizado
        if (adminRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Erro: Email já utilizado!");
        }

        // Criptografa a senha
        String encodedPassword = encoder.encode(signUpRequest.getSenha());

        // Cria o administrador
        Administrador admin = new Administrador(signUpRequest.getNome(), signUpRequest.getEmail(), encodedPassword, "ROLE_ADMIN");
        adminRepository.save(admin);

        return ResponseEntity.ok("Administrador cadastrado com sucesso!");
    }
}
