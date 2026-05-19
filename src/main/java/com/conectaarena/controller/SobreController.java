package com.conectaarena.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SobreController {

    @GetMapping("/sobre")
    public String exibirSobre(Model view) {
        view.addAttribute("paginaAtiva", "sobre");
        return "sobre";
    }
}