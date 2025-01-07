package br.com.desafioresidencia.gerenciadoreventos.dtos;

import java.time.LocalDate;

public class EventoRequestDTO {
	
	    private String nome;
	    private LocalDate data;
	    private String localizacao;
	    private String imagem; 
	   
	    
	    public EventoRequestDTO(String nome, LocalDate data, String localizacao, String imagem, Long adminId) {
	        this.nome = nome;
	        this.data = data;
	        this.localizacao = localizacao;
	        this.imagem = imagem;
	        
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
	    
	    

}
