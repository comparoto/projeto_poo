package com.conectaarena.service;

import com.conectaarena.model.Usuario;
import com.conectaarena.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService{
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }
    public Usuario cadastrarUsuario(Usuario usuario){
        if (usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new IllegalArgumentException("Este e-mail já está cadastrado!");
        }
        if (usuarioRepository.existsByCpf(usuario.getCpf())){
            throw new IllegalArgumentException("Este CPF já está cadastrado!");
        }
        return usuarioRepository.save(usuario);
    }
    public Optional<Usuario> autenticar(String email, String senha){
        return usuarioRepository.findByEmail(email)
                .filter(u -> u.getSenha().equals(senha));
    }
}