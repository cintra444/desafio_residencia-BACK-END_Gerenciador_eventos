package br.com.desafioresidencia.gerenciadoreventos.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.desafioresidencia.gerenciadoreventos.security.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			// Extrair o token do cabeçalho Authorization
			String token = extrairToken(request);

			// Verificar se o token é válido
			if (token != null && jwtUtil.verificarToken(token)) {
				// Extrair o email do token
				String email = jwtUtil.extrairEmail(token);

				if (email != null) {
					// Configurar o contexto de autenticação
					SecurityContextHolder.getContext()
							.setAuthentication(new JwtAuthentication(email));
				}
			}
		} catch (Exception ignored) {
		}

		filterChain.doFilter(request, response);
	}

	private String extrairToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");

		if (header != null && header.startsWith("Bearer ")) {
			return header.substring(7); // Remover o "Bearer " do token
		}

		return null;
	}
}
