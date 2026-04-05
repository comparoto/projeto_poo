package com.conectaarena.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "eventos")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private LocalDateTime data;
    private String categoria;
    private Integer capacidadeMaxima;
    private Integer ingressosVendidos = 0;
    private String urlImagem; // <-- ADICIONE ISSO

    public Evento() {}

    public Evento(String titulo, LocalDateTime data, String categoria, Integer capacidade, String urlImagem) {
        this.titulo = titulo;
        this.data = data;
        this.categoria = categoria;
        this.capacidadeMaxima = capacidade;
        this.urlImagem = urlImagem;
        this.ingressosVendidos = 0;
    }

    public boolean temVaga() {
        return this.ingressosVendidos < this.capacidadeMaxima;
    }

    public void registrarVenda() {
        if (!temVaga()) {
            throw new IllegalStateException("Capacidade máxima atingida!");
        }
        this.ingressosVendidos++;
    }

    // getters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public LocalDateTime getData() { return data; }
    public String getCategoria() { return categoria; }
    public Integer getCapacidadeMaxima() { return capacidadeMaxima; }
    public Integer getIngressosVendidos() { return ingressosVendidos; }
    public String getUrlImagem() { return urlImagem; }
}