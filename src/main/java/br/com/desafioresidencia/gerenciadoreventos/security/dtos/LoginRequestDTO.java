package br.com.desafioresidencia.gerenciadoreventos.security.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para transporte de dados do login do administrador.")
public class LoginRequestDTO {

	@Schema(description = "Email do administrador para autenticação.", example = "admin@dominio.com")
	@NotBlank(message = "O email não pode estar vazio.")
	@Email(message = "O email deve ter um formato válido.")
	private final String email;

	@Schema(description = "Senha do administrador para autenticação.", example = "senhaSegura123")
	@NotBlank(message = "A senha não pode estar vazia.")
	private final String senha;

	public LoginRequestDTO(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}
}
