package br.com.desafioresidencia.gerenciadoreventos.security.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "administrador")
public class Administrador {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ad_cd_adminId")
	private Long id;

	@NotBlank(message = "O nome não pode estar em branco.")
	@Column(name = "ad_tx_nome")
	private String nome;

	@NotNull(message = "O email não pode ser nulo.")
	@NotBlank(message = "O email não pode estar em branco.")
	@Email(message = "O email deve ser válido.")
	@Column(name = "ad_tx_email", unique = true)
	private String email;

	@NotBlank(message = "A senha não pode estar em branco.")
	@Column(name = "ad_tx_senha")
	private String senha;

	@Column(name = "ad_tx_role", nullable = false)
	private final String role = "ROLE_ADMIN"; // Campo fixo para ROLE_ADMIN

	// Construtor padrão
	public Administrador() {
	}

	// Construtor com campos (exceto ID, pois é gerado pelo banco)
	public Administrador(String nome, String email, String senha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	// Construtor completo (inclui ID - para testes ou consultas específicas)
	public Administrador(Long id, String nome, String email, String senha) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
	}

	public Administrador(Object object, String nome, String email,
			String encodedPassword, String string) {
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public String getRole() {
		return role;
	}

	// Setters (remover setRole, pois é fixo)
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
