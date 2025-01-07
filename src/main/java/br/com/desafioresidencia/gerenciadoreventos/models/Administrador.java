package br.com.desafioresidencia.gerenciadoreventos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrador")
public class Administrador {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ad_cd_idadmin")
	private Long id;
	
	@Column(name = "ad_tx_nome")
	private String name;
	
	@Column(name = "ad_tx_email")
	private String email;
	
	@Column(name = "ad_tx_senha")
	private String senha;
	
	@Column(name = "ad_tx_role")
	private String role;

	public Administrador() {
			
	}

	public Administrador(Long id, String name, String email, String senha,
			String role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.senha = senha;
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}
