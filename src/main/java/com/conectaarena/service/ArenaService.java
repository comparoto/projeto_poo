package com.conectaarena.service;

import com.conectaarena.model.*; // importa as entidades
import com.conectaarena.repository.*; // importa todas as interfaces
import org.springframework.stereotype.Service; // anotação service
import org.springframework.transaction.annotation.Transactional; // anotação transactional
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID; // gerar ids únicos (qrCodes)
import java.util.stream.Collectors;

@Service
public class ArenaService {

    private final EventoRepository eventoRepository;
    private final IngressoRepository ingressoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SugestaoRepository sugestaoRepository;
    private final AgendamentoRepository agendamentoRepository; // Injetado de forma real

    public ArenaService(EventoRepository eventoRepository, IngressoRepository ingressoRepository, UsuarioRepository usuarioRepository,
                        SugestaoRepository sugestaoRepository,
                        AgendamentoRepository agendamentoRepository) {
        this.eventoRepository = eventoRepository;
        this.ingressoRepository = ingressoRepository;
        this.usuarioRepository = usuarioRepository;
        this.sugestaoRepository = sugestaoRepository;
        this.agendamentoRepository = agendamentoRepository;
    }

    // ==========================================================================
    // FLUXO DE EVENTOS (VITRINE PÚBLICA, ADMIN & ORGANIZADORES)
    // ==========================================================================

    // Listando os eventos na Home com filtro de data (Apenas futuros)
    public List<Evento> listarEventos(String categoria) {
        if (categoria == null || categoria.isBlank() || categoria.equalsIgnoreCase("Todos")) {
            return eventoRepository.findByDataAfterOrderByDataAsc(LocalDateTime.now());
        }
        return eventoRepository.findByCategoriaAndDataAfterOrderByDataAsc(categoria, LocalDateTime.now());
    }

    // Retorna todos os eventos do banco sem filtro de data para gestão geral do Admin
    public List<Evento> listarTodosEventosParaAdmin() {
        return eventoRepository.findAll();
    }

    // Filtra para exibir APENAS os eventos cadastrados pelo Organizador Logado
    public List<Evento> listarEventosPorOrganizador(int organizadorId) {
        return eventoRepository.findByOrganizadorIdOrderByDataAsc(organizadorId);
    }

    // Método antigo de salvar evento (Mantido por compatibilidade caso usem em algum teste)
    @Transactional
    public Evento salvarEvento(Evento evento) {
        if (evento == null) {
            throw new IllegalArgumentException("O evento não pode ser nulo!");
        }
        return eventoRepository.save(evento);
    }

    // NOVO MÉTODO: Salva o evento vinculando o Organizador dono
    @Transactional
    public Evento salvarEvento(Evento evento, int organizadorId) {
        if (evento == null) {
            throw new IllegalArgumentException("O evento não pode ser nulo!");
        }

        Optional<Usuario> orgOpt = usuarioRepository.findById(organizadorId);
        if (orgOpt.isEmpty()) {
            throw new RuntimeException("Organizador não localizado para criar o evento.");
        }

        // Vincula o organizador logado como dono do evento antes de salvar
        evento.setOrganizador(orgOpt.get());
        return eventoRepository.save(evento);
    }

    // ==========================================================================
    // FLUXO DE COMPRAS & INGRESSOS
    // ==========================================================================

    @Transactional
    public Ingresso comprarIngresso(int eventoId, int usuarioId) {
        Optional<Evento> eventoOptional = eventoRepository.findById(eventoId);
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioId);

        if (eventoOptional.isEmpty()) {
            throw new RuntimeException("Evento não encontrado: " + eventoId);
        }
        Evento evento = eventoOptional.get();

        if (usuarioOptional.isEmpty()) {
            throw new RuntimeException("Usuário inexistente: " + usuarioId);
        }
        Usuario usuario = usuarioOptional.get();

        if (evento.getIngressosVendidos() >= evento.getIngressosDisponiveis()) {
            throw new RuntimeException("Ingressos esgotados para este evento!");
        }

        evento.setIngressosVendidos(evento.getIngressosVendidos() + 1);
        eventoRepository.save(evento);

        String qrCode = "ARENA-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Ingresso novoIngresso = new Ingresso(0, usuario, evento, LocalDateTime.now(), qrCode);

        return ingressoRepository.save(novoIngresso);
    }

    public List<Ingresso> listarIngressosPorUsuario(int usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            return java.util.Collections.emptyList();
        }
        return ingressoRepository.findByComprador(usuarioOpt.get());
    }

    // ==========================================================================
    // FLUXO DE MURAL DE SUGESTÕES
    // ==========================================================================

    public List<Sugestao> listarTodasSugestoes() {
        return sugestaoRepository.findAllByOrderByDataCriacaoDesc();
    }

    public List<Sugestao> listarSugestoesPorUsuario(int usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            return java.util.Collections.emptyList();
        }

        String marcadorEmail = "[" + usuarioOpt.get().getEmail() + "]";
        return sugestaoRepository.findAllByOrderByDataCriacaoDesc().stream()
                .filter(s -> s.getTexto() != null && s.getTexto().contains(marcadorEmail))
                .collect(Collectors.toList());
    }

    @Transactional
    public void salvarSugestao(String texto) {
        Sugestao novaSugestao = new Sugestao(texto);
        sugestaoRepository.save(novaSugestao);
    }

    @Transactional
    public void salvarSugestaoComUsuario(String texto, int usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado para vincular a sugestão.");
        }

        String textoComTag = texto + " [" + usuarioOpt.get().getEmail() + "]";
        Sugestao novaSugestao = new Sugestao(textoComTag);
        sugestaoRepository.save(novaSugestao);
    }

    // ==========================================================================
    // FLUXO REAL DE AGENDAMENTOS DE VISITA (DIRETO NO BANCO)
    // ==========================================================================

    // Busca os agendamentos reais do usuário salvos no banco
    public List<Agendamento> listarAgendamentosPorUsuario(int usuarioId) {
        return agendamentoRepository.findByUsuarioId(usuarioId);
    }

    // RECUPERADO: Salva o agendamento de forma real associando a chave estrangeira do usuário
    @Transactional
    public void salvarAgendamento(String dataVisita, String horario, int quantidadePessoas, int usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado para realizar o agendamento.");
        }

        Agendamento novoAgendamento = new Agendamento(0, usuarioOpt.get(), dataVisita, horario, quantidadePessoas);
        agendamentoRepository.save(novoAgendamento);
    }

    // ==========================================================================
    // FLUXO DE EXCLUSÃO DE CONTA (ZONA DE PERIGO)
    // ==========================================================================

    @Transactional
    public void excluirUsuarioDoSistema(int usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não localizado no sistema.");
        }
        Usuario usuario = usuarioOpt.get();

        // 1. Remove os ingressos do usuário (FK)
        List<Ingresso> ingressosDoUsuario = ingressoRepository.findByComprador(usuario);
        ingressoRepository.deleteAll(ingressosDoUsuario);

        // 2. Remove as sugestões que contêm a Tag de e-mail do usuário
        String marcadorEmail = "[" + usuario.getEmail() + "]";
        List<Sugestao> sugestoesDoUsuario = interstateSugestoesParaDelecao(marcadorEmail);
        sugestaoRepository.deleteAll(sugestoesDoUsuario);

        // 3. Remove todos os agendamentos do usuário antes de apagar a conta
        agendamentoRepository.deleteByUsuarioId(usuarioId);

        // 4. Por fim, deleta o registro principal do Usuário
        usuarioRepository.deleteById(usuarioId);
    }

    private List<Sugestao> interstateSugestoesParaDelecao(String marcador) {
        return sugestaoRepository.findAll().stream()
                .filter(s -> s.getTexto() != null && s.getTexto().contains(marcador))
                .collect(Collectors.toList());
    }
}