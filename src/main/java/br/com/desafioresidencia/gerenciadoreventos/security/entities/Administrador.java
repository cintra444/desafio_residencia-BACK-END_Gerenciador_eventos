package br.com.desafioresidencia.gerenciadoreventos.security.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "administrador")
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O nome é obrigatório")
    @Column(name = "ad_tx_nome", nullable = false)
    private String nome;

    @NotEmpty(message = "O email é obrigatório")
    @Email(message = "O email deve ser válido")
    @Column(name = "ad_tx_email", unique = true, nullable = false)
    private String email;

    @NotEmpty(message = "A senha é obrigatória")
    @Column(name = "ad_tx_senha", nullable = false)
    private String senha;

    
    public Administrador() {
    }

    
    public Administrador(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha; // Senha deve ser codificada antes de salvar
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

    // Método auxiliar para codificar a senha
    public void codificarSenha() {
        this.senha = new BCryptPasswordEncoder().encode(this.senha);
    }
}
