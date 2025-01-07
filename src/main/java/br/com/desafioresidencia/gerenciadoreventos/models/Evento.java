package br.com.desafioresidencia.gerenciadoreventos.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "evento")
public class Evento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ev_cd_idev")
	private Long id;
	
	@Column(name = "ev_tx_nome")
	private String nome;
	
	@Column(name = "ev_dt_data")
	private LocalDate data;
	
	@Column(name = "ev_tx_localizacao")
	private String localizacao;
	
	@Column(name = "ev_img_imagem")
	private String imagem;
	
	@ManyToOne
	@JoinColumn(name = "ad_cd_idadmin", nullable = false)
	private Administrador admin;
	
	public Evento() {
		
	}
	
	public Evento(Long id, String nome, LocalDate data, String localizacao, String imagem, Administrador admin) {
		super();
		this.id = id;
		this.nome = nome;
		this.data = data;
		this.localizacao = localizacao;
		this.imagem = imagem;
		this.admin = admin;
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

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}
	
}
