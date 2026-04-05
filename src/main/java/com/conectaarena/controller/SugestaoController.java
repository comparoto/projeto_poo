package com.conectaarena.controller;

import com.conectaarena.service.ArenaService;
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
    public String receberSugestao(@RequestParam String texto, RedirectAttributes redirect) {
        try {
            arenaService.salvarSugestao(texto);
            redirect.addFlashAttribute("msgSucesso", "Obrigado! Sua sugestão foi enviada.");
        } catch (Exception e) {
            redirect.addFlashAttribute("msgErro", e.getMessage());
        }
        return "redirect:/";
    }
}