package br.com.desafioresidencia.gerenciadoreventos.security.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para representar a resposta do token JWT.")
public class JwtResponseDTO {

    @Schema(description = "Token JWT gerado para autenticação.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private final String token;

    @Schema(description = "Mensagem de resposta, caso aplicável.", example = "Login realizado com sucesso!")
    private final String message;

    @Schema(description = "Tipo do token, geralmente 'Bearer'.", example = "Bearer")
    private final String type;

    public JwtResponseDTO(String token, String message, String type) {
        this.token = token;
        this.message = message;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }
}
