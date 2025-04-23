package br.com.desafioresidencia.gerenciadoreventos.configs;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Usuario;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.logging.Logger;

@Configuration
public class DataLoader {

    private static final Logger logger = Logger.getLogger(DataLoader.class.getName());

    @Bean
    public CommandLineRunner loadData(UsuarioRepository repository, PasswordEncoder passwordEncoder) {

        return args -> {
            if (repository.findByEmail("admin@admin.com").isEmpty()) {

                // Cria o administrador
                Usuario admin = new Usuario();
                admin.setEmail("admin@admin.com");
                admin.setSenha(passwordEncoder.encode("admin123"));
                admin.setNome("Administrador");
                admin.setRole("ROLE_ADMIN");
                repository.save(admin);

                logger.info("Administrador criado com sucesso.");
            } else{

                logger.info("Administrador ja cadastrado.");
            }
        };
    }
}
