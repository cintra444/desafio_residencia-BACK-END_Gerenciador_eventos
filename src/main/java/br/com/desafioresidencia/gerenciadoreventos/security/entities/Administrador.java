package br.com.desafioresidencia.gerenciadoreventos.security.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "administrador")
public class Administrador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "O nome não pode ser nulo.")
	@Column(name = "ad_tx_nome")
	private String nome;

	@NotNull(message = "O email não pode ser nulo.")
	@Email(message = "O email deve ser válido.")
	@Column(name = "ad_tx_email", unique = true)
	private String email;

	@NotNull(message = "A senha não pode ser nulo.")
	@Column(name = "ad_tx_senha")
	private String senha;

	public Administrador(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		this.senha = new BCryptPasswordEncoder().encode(senha);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

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

}
