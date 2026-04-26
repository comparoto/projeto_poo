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
    private String urlImagem;
    private double preco;

    public Evento() {}

    public Evento(String titulo, LocalDateTime data, String categoria, Integer capacidade, String urlImagem, double preco) {
        this.titulo = titulo;
        this.data = data;
        this.categoria = categoria;
        this.capacidadeMaxima = capacidade;
        this.urlImagem = urlImagem;
        this.preco = preco;
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
    public double getPreco() { return preco; } // <-- ESSENCIAL PARA O THYMELEAF

    // setters
    public void setPreco(double preco) { this.preco = preco; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setData(LocalDateTime data) { this.data = data; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setCapacidadeMaxima(Integer capacidadeMaxima) { this.capacidadeMaxima = capacidadeMaxima; }
    public void setIngressosVendidos(Integer ingressosVendidos) { this.ingressosVendidos = ingressosVendidos; }
    public void setUrlImagem(String urlImagem) { this.urlImagem = urlImagem; }
}