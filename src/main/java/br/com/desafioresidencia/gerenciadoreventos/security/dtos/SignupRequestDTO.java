package br.com.desafioresidencia.gerenciadoreventos.security.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequestDTO {
	
	@NotBlank(message = "O email é obrigatório.")
	@Email(message = "O email deve ser válido.")
	private String email;
	
	@NotBlank(message = "A senha é obrigatória.")
	@Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
	private String senha;
	
	@NotBlank(message = "O nome é obrigatório.")
	private String nome;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
