package br.com.desafioresidencia.gerenciadoreventos.security.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ev_cd_idev")
    private Long id;

    @NotBlank(message = "O nome do evento é obrigatório.")
    @Column(name = "ev_tx_nome", nullable = false)
    private String nome;

    @NotNull(message = "A data do evento é obrigatória.")
    @Column(name = "ev_dt_data", nullable = false)
    private LocalDate data;

    @NotBlank(message = "A localização do evento é obrigatória.")
    @Column(name = "ev_tx_localizacao", nullable = false)
    private String localizacao;

    @Column(name = "ev_img_imagem")
    private String imagem;

    @ManyToOne(optional = false) // Relacionamento obrigatório
    @JoinColumn(name = "ad_cd_idadmin", nullable = false)
    private Administrador admin;

    // Construtor padrão
    public Evento() {
    }

    // Construtor sem ID (para criação de eventos)
    public Evento(String nome, LocalDate data, String localizacao, String imagem, Administrador admin) {
        this.nome = nome;
        this.data = data;
        this.localizacao = localizacao;
        this.imagem = imagem;
        this.admin = admin;
    }

    // Construtor completo (inclui ID - para testes ou consultas específicas)
    public Evento(Long id, String nome, LocalDate data, String localizacao, String imagem, Administrador admin) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.localizacao = localizacao;
        this.imagem = imagem;
        this.admin = admin;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getData() {
        return data;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getImagem() {
        return imagem;
    }

    public Administrador getAdmin() {
        return admin;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setAdmin(Administrador admin) {
        this.admin = admin;
    }
}
