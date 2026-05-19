package com.conectaarena.controller;

import com.conectaarena.model.Evento;
import com.conectaarena.model.Usuario;
import com.conectaarena.service.ArenaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private final ArenaService arenaService;

    public HomeController(ArenaService arenaService) {
        this.arenaService = arenaService;
    }

    @GetMapping
    public String exibirHome(@RequestParam(required = false) String categoria, @RequestParam(required = false, defaultValue = "false") boolean verMais, HttpSession session, Model view) {

        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");
        view.addAttribute("usuario", logado);

        List<Evento> listaDeEventos = arenaService.listarEventos(categoria);
        view.addAttribute("eventos", listaDeEventos);

        String nomeCategoria;
        if (categoria != null && !categoria.isEmpty()) {
            nomeCategoria = categoria;
        } else {
            nomeCategoria = "Todos";
        }
        view.addAttribute("categoriaSelecionada", nomeCategoria);

        if (verMais) {
            view.addAttribute("paginaAtiva", "eventos");
        } else {
            view.addAttribute("paginaAtiva", "home");
        }

        return "home";
    }
    @GetMapping("/visitas/agendar")
    public String exibirPaginaAgendamento(Model model) {
        model.addAttribute("paginaAtiva", "agendamento");
        return "agendamento";
    }

}