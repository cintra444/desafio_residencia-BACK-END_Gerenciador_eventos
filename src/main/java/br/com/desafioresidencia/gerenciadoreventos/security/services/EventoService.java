package br.com.desafioresidencia.gerenciadoreventos.security.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.desafioresidencia.gerenciadoreventos.security.dtos.EventoRequestDTO;
import br.com.desafioresidencia.gerenciadoreventos.security.entities.Administrador;
import br.com.desafioresidencia.gerenciadoreventos.security.entities.Evento;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.AdminRepository;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.EventoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.io.IOException;

@Service
public class EventoService {

    private static final Logger logger = LoggerFactory.getLogger(EventoService.class);

    @Autowired
    EventoRepository eventoRepository;

    @Autowired
    AdminRepository adminRepository;

    // Limite de tamanho do arquivo (10MB)
    private static final long MAX_SIZE = 10 * 1024 * 1024;

    // listar eventos
    public List<Evento> listarTodosEventos() {
        return eventoRepository.findAll();
    }

    public List<Evento> listarEventosPorAdmin(Long adminId) {
        return eventoRepository.findByAdminId(adminId);
    }

    // cadastrar evento
    public Evento cadastrarEvento(Long adminId, EventoRequestDTO eventoDTO) {
        Administrador admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new RuntimeException("Erro: Administrador não encontrado!"));

        Evento evento = new Evento(null, eventoDTO.getNome(),
                eventoDTO.getData(), eventoDTO.getLocalizacao(),
                eventoDTO.getImagem(), admin);
        return eventoRepository.save(evento);
    }

    public Evento atualizarEvento(Long eventoId, EventoRequestDTO eventoDTO) {
        Evento evento = eventoRepository.findById(eventoId).orElseThrow(
                () -> new RuntimeException("Erro: Evento não encontrado"));

        if (eventoDTO.getNome() != null) {
            evento.setNome((eventoDTO.getNome()));
        }
        if (eventoDTO.getData() != null) {
            evento.setData(eventoDTO.getData());
        }
        if (eventoDTO.getLocalizacao() != null) {
            evento.setLocalizacao(eventoDTO.getLocalizacao());
        }

        return eventoRepository.save(evento);
    }

    public void deletarEvento(Long eventoId) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Erro: Evento não encontrado!"));
        eventoRepository.delete(evento);
    }

    public String salvarImagem(MultipartFile file) throws IOException {
        // Verifica o tipo do arquivo (apenas JPG e PNG)
        String tipoArquivo = file.getContentType();
        if (!tipoArquivo.equals("image/jpeg") && !tipoArquivo.equals("image/png")) {
            throw new IllegalArgumentException("Tipo de arquivo inválido. Somente JPG e PNG são permitidos.");
        }

        // Verifica o tamanho do arquivo
        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("O arquivo é muito grande. O tamanho máximo permitido é 10MB.");
        }

        // Diretório onde as imagens serão armazenadas
        String diretorio = "caminho/para/armazenar/imagens/";

        // Verifica se o diretório existe, caso contrário, cria
        Path pathDiretorio = Paths.get(diretorio);
        if (!Files.exists(pathDiretorio)) {
            try {
                Files.createDirectories(pathDiretorio);
            } catch (java.io.IOException e) {
                logger.error("Erro ao criar o diretório para imagens", e);
                throw new RuntimeException("Erro ao criar o diretório para armazenar as imagens.");
            }
        }

        // Nome único para o arquivo
        String nomeArquivo = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path caminhoArquivo = pathDiretorio.resolve(nomeArquivo);

        // Salva o arquivo localmente
        try {
            Files.write(caminhoArquivo, file.getBytes());
        } catch (java.io.IOException e) {
            logger.error("Erro ao salvar a imagem", e);
            throw new RuntimeException("Erro ao salvar a imagem.");
        }

        // Retorna a URL ou caminho do arquivo
        return "/imagens/" + nomeArquivo;
    }

    public void associarImagemAoEvento(Long eventoId, String url) {
        Evento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        evento.setImagem(url);
        eventoRepository.save(evento);
    }
}
