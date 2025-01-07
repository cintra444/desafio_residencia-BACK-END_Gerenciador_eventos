package br.com.desafioresidencia.gerenciadoreventos.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.AdminRepository;

@Service
public class AdministradorService {

	@Autowired
	private AdminRepository adminRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public Administrador cadastrarAdministrador(Administrador admin) {

		if (adminRepository.existsByEmail(admin.getEmail())) {
			throw new RuntimeException(
					"Administrador com esse email já cadastrado");
		}

		admin.setSenha(passwordEncoder.encode(admin.getSenha()));

		return adminRepository.save(admin);
	}

}
