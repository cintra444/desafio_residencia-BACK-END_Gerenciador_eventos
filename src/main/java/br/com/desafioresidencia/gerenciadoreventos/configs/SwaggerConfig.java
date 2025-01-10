package br.com.desafioresidencia.gerenciadoreventos.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo()) 
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", securityScheme())) // Adiciona o esquema de segurança JWT
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth")); // Aplica segurança a todos os endpoints
    }

    // Informações sobre a API
    private Info apiInfo() {
        return new Info()
                .title("Gerenciador de Eventos API")
                .description("API para gerenciar eventos no desafio da residência.")
                .version("1.0.0")
                .contact(new Contact()
                        .name("Eber Cintra")
                        .url("URL do Portfólio ou LinkedIn")
                        .email("desafioneki@gmail.com"));
    }

    // Configuração de segurança JWT
    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT") // 
                .description("Insira o token JWT no formato: Bearer {seu token}");
    }
}
