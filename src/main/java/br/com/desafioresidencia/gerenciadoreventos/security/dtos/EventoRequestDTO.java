package br.com.desafioresidencia.gerenciadoreventos.security.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para criar ou atualizar um evento.")
public class EventoRequestDTO {

	@Schema(description = "Nome do evento", example = "Feira de Tecnologia", required = true)
	@NotBlank(message = "O nome do evento é obrigatório.")
	private String nome;

	@Schema(description = "Data do evento", example = "2025-12-31", required = true)
	@NotNull(message = "A data do evento é obrigatória.")
	@FutureOrPresent(message = "A data do evento não pode estar no passado.")
	private LocalDate data;

	@Schema(description = "Localização do evento", example = "Centro de Convenções", required = true)
	@NotBlank(message = "A localização do evento é obrigatória.")
	private String localizacao;

	@Schema(description = "URL da imagem representando o evento", example = "http://meusite.com/imagem.jpg")
	private String imagem;

	public EventoRequestDTO() {
	}

	public EventoRequestDTO(String nome, LocalDate data, String localizacao,
			String imagem) {
		this.nome = nome;
		this.data = data;
		this.localizacao = localizacao;
		this.imagem = imagem;
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

}