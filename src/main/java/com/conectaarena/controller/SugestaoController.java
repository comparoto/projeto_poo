package com.conectaarena.controller;

import com.conectaarena.model.Usuario;
import com.conectaarena.service.ArenaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sugestoes")
public class SugestaoController {

    private final ArenaService arenaService;

    public SugestaoController(ArenaService arenaService) {
        this.arenaService = arenaService;
    }

    @PostMapping
    public String receberSugestao(@RequestParam String texto, HttpSession session, RedirectAttributes redirecionar) {
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");

        try {
            if (logado != null) {
                arenaService.salvarSugestaoComUsuario(texto, logado.getId());
                redirecionar.addFlashAttribute("sucesso", "Obrigado! Sua sugestão foi gravada no seu histórico.");
                return "redirect:/usuario/dashboard";
            } else {
                return "redirect:/";
            }
        } catch (RuntimeException excecao) {
            redirecionar.addFlashAttribute("msgErro", excecao.getMessage());
            return "redirect:/";
        }
    }
}