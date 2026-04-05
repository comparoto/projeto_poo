package com.conectaarena.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sugestoes")
public class Sugestao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String texto;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    public Sugestao() {}
    public Sugestao(String texto) { this.texto = texto; }

    // getters e setters
    public Long getId() { return id; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}