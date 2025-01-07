package br.com.desafioresidencia.gerenciadoreventos.security.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "DTO para atualizar informações de um evento.")
public class EventoUpdateDTO {

    @FutureOrPresent(message = "A data do evento não pode estar no passado.")
    @Schema(description = "Nova data do evento (opcional). Deve ser uma data no presente ou futuro.", example = "2025-12-31")
    private LocalDate data;

    @Schema(description = "Nova localização do evento (opcional).", example = "Centro de Convenções")
    private String localizacao;

    // Construtor padrão
    public EventoUpdateDTO() {
    }

    // Getters e Setters
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
}
