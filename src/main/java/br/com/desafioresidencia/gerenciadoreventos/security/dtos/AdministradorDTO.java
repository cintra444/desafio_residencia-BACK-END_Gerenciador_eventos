package br.com.desafioresidencia.gerenciadoreventos.security.dtos;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Administrador;

public class AdministradorDTO {

    private Long id;
    private String nome;
    private String email;

    public AdministradorDTO(Administrador administrador) {
        this.id = administrador.getId();
        this.nome = administrador.getNome();
        this.email = administrador.getEmail();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
