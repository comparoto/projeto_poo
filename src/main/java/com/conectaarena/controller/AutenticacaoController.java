package com.conectaarena.controller;

import com.conectaarena.model.Usuario;
import com.conectaarena.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Optional;

//Adicionar autenticação de senha.
//Adicionar autenticação de telefone.

@Controller
public class AutenticacaoController {

    private final UsuarioService usuarioService;

    public AutenticacaoController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String telaLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String efetuarLogin(@RequestParam String email, @RequestParam String senha, HttpSession session, Model model) {

        Optional<Usuario> usuarioOpt = usuarioService.autenticar(email, senha);

        if (usuarioOpt.isPresent()){
            Usuario usuario = usuarioOpt.get();
            session.setAttribute("usuarioLogado", usuario);

            if ("ADMIN".equalsIgnoreCase(usuario.getPerfil())) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/";
            }
        }

        model.addAttribute("erro", "E-mail ou senha inválidos.");
        return "login";
    }

    @GetMapping("/cadastro")
    public String telaCadastro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String efetuarCadastro(
            @Valid @ModelAttribute("usuario") Usuario novoUsuario,
            BindingResult result,
            @RequestParam(required = false) String chaveAdmin,
            Model model) {
        try {
            if (result.hasErrors()) {
                return "cadastro";
            }
            if(novoUsuario.getDataNascimento() != null){
                if(novoUsuario.getDataNascimento().isBefore(LocalDate.now().minusYears(100))){
                    model.addAttribute("erro", "Data de nascimento inválida. A idade não pode ser superior a 100 anos.");
                    return "cadastro";
                }
            }
            if ("ADMIN".equalsIgnoreCase(novoUsuario.getPerfil())) {
                if (!"ARENA2026".equals(chaveAdmin)) {
                    throw new IllegalArgumentException("Chave de acesso corporativo inválida!");
                }
            } else {
                novoUsuario.setId(0);
            }

            usuarioService.cadastrarUsuario(novoUsuario);
            return "redirect:/login?sucesso=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastro";
        }
    }

    @GetMapping("/logout")
    public String efetuarLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}