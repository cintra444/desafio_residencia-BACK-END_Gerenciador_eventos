package br.com.desafioresidencia.gerenciadoreventos.security.dtos;

import java.time.LocalDate;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Evento;

public class EventoDTO {

    private Long id;
    private String nome;
    private LocalDate data;
    private String localizacao;
    private String imagem;
    private Long adminId;

    // Construtor padrão
    public EventoDTO() {
    }

    // Construtor que recebe a entidade Evento
    public EventoDTO(Evento evento) {
        this.id = evento.getId();
        this.nome = evento.getNome();
        this.data = evento.getData();
        this.localizacao = evento.getLocalizacao();
        this.imagem = evento.getImagem();
        this.adminId = evento.getAdministrador() != null ? evento.getAdministrador().getId() : null;
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

    public void setImagemUrl(String imagem) {
        this.imagem = imagem;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
