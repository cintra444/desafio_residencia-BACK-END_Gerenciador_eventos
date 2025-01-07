package br.com.desafioresidencia.gerenciadoreventos.dtos;

public class JwtResponseDTO {

	private String token;
	private String message;
	
	
	
	public JwtResponseDTO(String token, String message, String type) {
		this.token = token;
		this.message = message;
		
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

	
	
	
}
