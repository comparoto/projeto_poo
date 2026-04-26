package com.conectaarena.controller;

import com.conectaarena.model.Evento;
import com.conectaarena.service.ArenaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    private final ArenaService arenaService;

    public HomeController(ArenaService arenaService) {
        this.arenaService = arenaService;
    }

    @GetMapping
    public String exibirHome(@RequestParam(required = false) String categoria, Model model) {
        List<Evento> listaDeEventos = arenaService.listarEventos(categoria);

        model.addAttribute("eventos", listaDeEventos);
        model.addAttribute("categoriaSelecionada", (categoria != null && !categoria.isEmpty()) ? categoria : "Todos");
        model.addAttribute("paginaAtiva", "home");

        return "home";
    }
}