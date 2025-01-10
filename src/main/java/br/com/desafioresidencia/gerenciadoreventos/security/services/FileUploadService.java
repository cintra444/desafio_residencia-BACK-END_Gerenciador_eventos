package br.com.desafioresidencia.gerenciadoreventos.security.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDirectory;

    @Value("${file.max-size}")
    private long maxFileSize;

    private static final Set<String> ALLOWED_CONTENT_TYPES = Set.of(
            "image/jpeg", "image/png", "image/gif"
    );

    public String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("O arquivo enviado está vazio.");
        }

        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("O arquivo excede o tamanho máximo permitido.");
        }

        if (!ALLOWED_CONTENT_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Tipo de arquivo não permitido. Envie uma imagem (JPEG, PNG ou GIF).");
        }

        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDirectory + File.separator + fileName);

        // Criação do diretório, se não existir
        Files.createDirectories(path.getParent());

        // Salvar o arquivo
        file.transferTo(path);

        return fileName;
    }
}
