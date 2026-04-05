package com.conectaarena.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String senha;
    private String perfil; // "ADMIN" ou "USER"

    public Usuario() {}

    public Usuario(String email, String senha, String perfil) {
        this.email = email;
        this.senha = senha;
        this.perfil = perfil;
    }

    // getters e setters manuais
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPerfil() { return perfil; }
    public String getSenha() { return senha; }
}