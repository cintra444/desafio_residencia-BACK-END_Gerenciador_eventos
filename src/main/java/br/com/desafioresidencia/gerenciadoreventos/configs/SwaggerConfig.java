package br.com.desafioresidencia.gerenciadoreventos.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@SecurityScheme(name = "Bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SwaggerConfig {

	@Value("${prop.swagger.dev-url}")
	private String devUrl; // URL de desenvolvimento (se necessário)

	@Bean
	public OpenAPI myOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("API para Gerenciamento de Eventos") // Título da API
				.version("1.0.0") // Versão da API
				.description("API para Gerenciamento de Eventos") // Descrição
																	// breve
				.contact(new Contact().name("Desafio Residência") // Nome para
																	// contato
						.email("desafioneki@gmail.com") // Email de contato
						.url("https://github.com/cintra444/desafio_residencia-BACK-END_Gerenciador_eventos") // Repositório
																												// do
																												// projeto
				))
				.components(new io.swagger.v3.oas.models.Components()
						.addSecuritySchemes("Bearer",
								new io.swagger.v3.oas.models.security.SecurityScheme()
										.type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
										.scheme("bearer").bearerFormat("JWT")
										.description(
												"JWT Token para Autenticação")));
	}

}
