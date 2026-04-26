package com.conectaarena.controller;

import com.conectaarena.model.Evento;
import com.conectaarena.service.ArenaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/eventos")
public class EventoController {

    private final ArenaService arenaService;

    public EventoController(ArenaService arenaService) {
        this.arenaService = arenaService;
    }

    @GetMapping
    public String listarEventos(@RequestParam(required = false) String categoria, Model model) {
        List<Evento> eventos = arenaService.listarEventos(categoria);
        model.addAttribute("eventos", eventos);
        model.addAttribute("categoriaSelecionada", (categoria == null || categoria.isBlank()) ? "Todos" : categoria);
        model.addAttribute("paginaAtiva", "eventos");
        return "home";
    }

    @GetMapping("/{id}")
    public String verDetalhes(@PathVariable Long id,
                              @RequestParam(required = false, defaultValue = "false") boolean comprando,
                              Model model) {
        Evento evento = arenaService.buscarPorId(id);
        model.addAttribute("evento", evento);
        model.addAttribute("comprando", comprando);
        model.addAttribute("paginaAtiva", "eventos");
        return "detalhes";
    }

    @PostMapping("/comprar")
    public String realizarCompra(@RequestParam Long eventoId,
                                 @RequestParam Long usuarioId,
                                 @RequestParam int quantidade,
                                 @RequestParam String formaPagamento,
                                 @RequestParam double precoUnitario,
                                 RedirectAttributes redirectAttributes) {
        try {
            double valorTotal = precoUnitario * quantidade;

            arenaService.comprarIngresso(eventoId, usuarioId);

            String mensagem = String.format("Sucesso! Você adquiriu %d ingresso(s) via %s. Valor total: R$ %.2f",
                    quantidade, formaPagamento, valorTotal);

            redirectAttributes.addFlashAttribute("msgSucesso", mensagem);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("msgErro", "Erro ao processar compra: " + e.getMessage());
        }

        return "redirect:/eventos/" + eventoId;
    }
}