package com.conectaarena.controller;

import com.conectaarena.model.Evento;
import com.conectaarena.service.ArenaService;
import com.conectaarena.repository.EventoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/eventos")//rota base que vai começar com /eventos
public class EventoController{

    //acessando o service e o repository
    private final ArenaService arenaService;
    private final EventoRepository eventoRepository;

    public EventoController(ArenaService arenaService, EventoRepository eventoRepository){
        this.arenaService = arenaService;
        this.eventoRepository = eventoRepository;
    }
    //buscando os dados e mandando pra home.html
    @GetMapping
    public String listar(String categoria, Model view){
        view.addAttribute("eventos", arenaService.listarEventos(categoria));//envia a lista de eventos para ser lida no html

        //filtragens para os botões do menu lateral continuarem funcionando
        String nomeCategoria;
        if (categoria != null && !categoria.isEmpty()) {
            nomeCategoria = categoria;
        } else {
            nomeCategoria = "Todos";
        }
        view.addAttribute("categoriaSelecionada", nomeCategoria);
        view.addAttribute("paginaAtiva", "eventos");

        return "home"; //retorna home.html
    }

    //buscando um evento específico pelo id direto no banco
    @GetMapping("/{id}")
    public String detalhes(@PathVariable int id, Model view){
        Evento evento = eventoRepository.findById(id).orElse(null);//se não encontrar o id no banco, retorna null

        if (evento == null){
            return "redirect:/eventos";//se o evento não existir,volta pra lista
        }

        view.addAttribute("evento", evento);
        view.addAttribute("paginaAtiva", "eventos");

        return "detalhes";//manda o objeto evento encontrado para detalhes.html
    }

    //comprando e usando RedirectAttributes para levar mensagens entre pgs
    @PostMapping("/comprar")
    public String comprar(int eventoId, int usuarioId, RedirectAttributes redirecionar){
        try{
            arenaService.comprarIngresso(eventoId, usuarioId);
            redirecionar.addFlashAttribute("mensagemSucesso", "Ingresso comprado!");
        }catch(RuntimeException excecao){
            redirecionar.addFlashAttribute("mensagemErro", excecao.getMessage());
        }
        return "redirect:/eventos/" + eventoId;//limpa o form e volta pra a pg do evento
    }
}