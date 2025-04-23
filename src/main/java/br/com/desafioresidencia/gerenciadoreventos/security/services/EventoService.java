package br.com.desafioresidencia.gerenciadoreventos.security.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.desafioresidencia.gerenciadoreventos.security.entities.Usuario;
import br.com.desafioresidencia.gerenciadoreventos.security.entities.Evento;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.UsuarioRepository;
import br.com.desafioresidencia.gerenciadoreventos.security.repositories.EventoRepository;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository administradorRepository;

    public Evento criarEvento(String nome, LocalDate data, String localizacao, Long adminId, String imagemPath) {
        Optional<Usuario> administradorOpt = administradorRepository.findById(adminId);
        
        if (administradorOpt.isEmpty()) {
            throw new IllegalArgumentException("Administrador não encontrado com o ID fornecido.");
        }
        
        Evento evento = new Evento();
        evento.setNome(nome);
        evento.setData(data);
        evento.setLocalizacao(localizacao);
        evento.setImagem(imagemPath);
        evento.setAdministrador(administradorOpt.get());

        return eventoRepository.save(evento);
    }

    public Evento obterEvento(Long id) {
        return eventoRepository.findById(id).orElse(null);
    }

    public List<Evento> listarTodosEventos(Long administradorId) {
        return eventoRepository.findByAdministradorId(administradorId);
    }

    public Evento cadastrarEvento(Evento evento) {
        return eventoRepository.save(evento);
    }

    public Evento atualizarEvento(Long eventoId, LocalDate data, String localizacao) {
        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);
        if (eventoOpt.isPresent()) {
            Evento evento = eventoOpt.get();
            evento.setData(data);
            evento.setLocalizacao(localizacao);
            return eventoRepository.save(evento);
        }
        return null;
    }

    public void excluirEvento(Long eventoId) {
        eventoRepository.deleteById(eventoId);
    }
}
