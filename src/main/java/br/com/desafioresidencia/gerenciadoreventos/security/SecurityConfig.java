package br.com.desafioresidencia.gerenciadoreventos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import br.com.desafioresidencia.gerenciadoreventos.security.services.AdminDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private AdminDetailsServiceImpl adminDetailsService;

	@Autowired
	private AuthEntryPointJWT unauthorizedHandler;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Configurações de segurança HTTP
		http.cors().and().csrf().disable()
			.exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler) // Trata falhas de autenticação
			.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Sem estado (stateless), ou seja, sem cookies de sessão
			.and()
			.authorizeRequests()
				.requestMatchers("/api/auth/**").permitAll()  // Permite acesso a rotas de autenticação sem autenticação
				.requestMatchers("/api/evento/**").hasRole("ADMIN")  // Apenas ADMIN pode acessar as rotas de evento
				.anyRequest().authenticated();  // Qualquer outra requisição requer autenticação

		// Adiciona o filtro que valida o token JWT
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();  // Retorna a configuração de segurança construída
	}

	// Cria o filtro para autenticação via JWT
	@Bean
	public JwtAuthenticationFilter authenticationJwtTokenFilter() {
		return new JwtAuthenticationFilter();
	}

	// Configura o provedor de autenticação com um serviço de detalhes de usuário customizado
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(adminDetailsService);  // Serviço para buscar os detalhes do usuário
		authProvider.setPasswordEncoder(passwordEncoder());  // Define o codificador de senha
		return authProvider;
	}

	// Define o codificador de senha (BCrypt)
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Define o AuthenticationManager bean
	@Bean
	public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManager.class);
	}
}
