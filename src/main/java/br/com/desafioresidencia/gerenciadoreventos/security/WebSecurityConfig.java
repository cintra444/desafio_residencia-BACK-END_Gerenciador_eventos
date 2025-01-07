package br.com.desafioresidencia.gerenciadoreventos.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.desafioresidencia.gerenciadoreventos.services.AdminDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private AdminDetailsServiceImpl adminDetailsService; // Use o AdminDetailsServiceImpl se aplicável

    @Autowired
    private AuthEntryPointJWT unauthorizedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Configuração CORS e CSRF
            .cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable())

            // Configuração para tratar erros de autenticação/autorizações
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))

            // Configuração de sessão stateless (sem sessão para segurança com JWT)
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Configuração de autorizações das rotas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Rotas públicas para autenticação
                .requestMatchers("/api/evento/**").hasRole("ADMIN") // Rotas protegidas por ROLE_ADMIN
                .anyRequest().authenticated()); // Qualquer outra rota precisa de autenticação

        // Configuração de provedor de autenticação
        http.authenticationProvider(authenticationProvider());

        // Filtro para interceptar tokens JWT antes do filtro de autenticação padrão
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Configuração de CORS
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Permitir qualquer origem (modifique para produção)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(Arrays.asList("Authorization")); // Expor cabeçalhos como o token JWT
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Filtro para autenticação com JWT
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    // Provedor de autenticação para verificar credenciais do administrador
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(adminDetailsService); // Use o serviço de detalhes do administrador
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Encoder de senha usando BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
