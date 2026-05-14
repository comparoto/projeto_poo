package com.conectaarena.controller;

import com.conectaarena.model.Evento;
import com.conectaarena.service.ArenaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/")//pg inicial
public class HomeController{
    private final ArenaService arenaService;

    public HomeController(ArenaService arenaService){
        this.arenaService = arenaService;
    }
    @GetMapping
    public String exibirHome(String categoria, Model view){
        List<Evento> listaDeEventos = arenaService.listarEventos(categoria);//buscado lista de eventos filtrada no service

        view.addAttribute("eventos", listaDeEventos);//manda lista de eventos para o th:each do thymeleaf

        //organizando filtragens
        String nomeCategoria;
        if (categoria != null && !categoria.isEmpty()){//vê se o usuário selecionou alguma categoria
            nomeCategoria = categoria;//se ss,usa o nome dela
        } else{
            nomeCategoria = "Todos";//se nn, o padrão é "Todos"
        }
        view.addAttribute("categoriaSelecionada", nomeCategoria);//manda o nome certo pra aparecer no título ou botão do html

        view.addAttribute("paginaAtiva", "home");//atributo pra controlar css ou partes ativas no html

        return "home";
    }
}