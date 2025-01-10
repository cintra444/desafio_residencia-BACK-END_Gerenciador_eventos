package br.com.desafioresidencia.gerenciadoreventos.security.entities;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ev_cd_idev")
    private Long id;

    @NotEmpty(message = "O nome do evento é obrigatório.")
    @Column(name = "ev_tx_nome", nullable = false)
    private String nome;

    @NotNull(message = "A data do evento é obrigatória.")
    @Column(name = "ev_dt_data", nullable = false)
    private LocalDate data;

    @NotEmpty(message = "A localização do evento é obrigatória.")
    @Column(name = "ev_tx_localizacao", nullable = false)
    private String localizacao;

    @Column(name = "ev_img_imagem")
    private String imagem;

    @ManyToOne
    @JoinColumn(name = "administrador_id", nullable = false)
    private Administrador administrador;

    // Construtor Padrão
    public Evento() {}

    // Construtor completo
    public Evento(String nome, LocalDate data, String localizacao, String imagem, Administrador administrador) {
        this.nome = nome;
        this.data = data;
        this.localizacao = localizacao;
        this.imagem = imagem;
        this.administrador = administrador;
    }

    // Getters e Setters
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
