package br.com.desafioresidencia.gerenciadoreventos.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.AdministradorRepository;

@Service
public class AdministradorService {

	@Autowired
	private AdministradorRepository adminRepository;

	public Administrador cadastrarAdministrador(Administrador admin) {

		if (adminRepository.existsByEmail(admin.getEmail())) {
			throw new RuntimeException(
					"Administrador com esse email já cadastrado");
		}

		return adminRepository.save(admin);
	}

}
