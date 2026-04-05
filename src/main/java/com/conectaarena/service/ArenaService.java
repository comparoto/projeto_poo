package com.conectaarena.service;

import com.conectaarena.model.*;
import com.conectaarena.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ArenaService {

    private final EventoRepository eventoRepo;
    private final IngressoRepository ingressoRepo;
    private final UsuarioRepository usuarioRepo;
    private final SugestaoRepository sugestaoRepo;


    public ArenaService(EventoRepository e, IngressoRepository i, UsuarioRepository u, SugestaoRepository s) {
        this.eventoRepo = e;
        this.ingressoRepo = i;
        this.usuarioRepo = u;
        this.sugestaoRepo = s;
    }

    //metodos de evento

    public List<Evento> listarEventos(String categoria) {
        LocalDateTime agora = LocalDateTime.now();
        if (categoria == null || categoria.isBlank() || categoria.equalsIgnoreCase("Todos")) {
            return eventoRepo.findByDataAfterOrderByDataAsc(agora);
        }
        return eventoRepo.findByCategoriaAndDataAfterOrderByDataAsc(categoria, agora);
    }

    public Evento buscarPorId(Long id) {
        return eventoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado: " + id));
    }

    // metodos de compraa

    @Transactional
    public Ingresso comprarIngresso(Long eventoId, Long usuarioId) {
        Evento evento = eventoRepo.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        Usuario usuario = usuarioRepo.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário inexistente"));

        if (!evento.temVaga()) {
            throw new RuntimeException("Ingressos Esgotados");
        }

        evento.registrarVenda();
        eventoRepo.save(evento);

        String qrCode = "ARENA-" + UUID.randomUUID().toString().substring(0, 8);
        Ingresso novoIngresso = new Ingresso(usuario, evento, qrCode);

        return ingressoRepo.save(novoIngresso);
    }

    // metodos de sugestao

    @Transactional
    public void salvarSugestao(String texto) {
        if (texto == null || texto.trim().length() < 20) {
            throw new RuntimeException("A sugestão deve ter pelo menos 20 caracteres.");
        }
        Sugestao novaSugestao = new Sugestao(texto);
        sugestaoRepo.save(novaSugestao);
    }

    public List<Sugestao> listarTodasSugestoes() {
        return sugestaoRepo.findAll();
    }
}