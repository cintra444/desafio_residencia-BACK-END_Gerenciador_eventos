package br.com.desafioresidencia.gerenciadoreventos.security.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ev_cd_idev")
    private Long id;

    @NotNull(message = "O nome do evento é obrigatória.")
    @Column(name = "ev_tx_nome", nullable = false)
    private String nome;

    @NotNull(message = "A data do evento é obrigatória.")
    @Column(name = "ev_dt_data", nullable = false)
    private LocalDate data;

    @NotNull(message = "A localização do evento é obrigatória.")
    @Column(name = "ev_tx_localizacao", nullable = false)
    private String localizacao;

    @Column(name = "ev_img_imagem")
    private String imagem;

    @ManyToOne
    @JoinColumn(name = "administrador_id")
    private Administrador administrador;

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

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}


}