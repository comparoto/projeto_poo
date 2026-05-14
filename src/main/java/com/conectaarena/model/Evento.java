package com.conectaarena.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "eventos")
public class Evento{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String titulo;
    private LocalDateTime data;
    private String categoria;
    private int ingressosDisponiveis;
    private int ingressosVendidos;
    private String imagem;
    private double preco;

    public Evento(){} // lembrando que o hibernate precisa desse construtor vazio

    public Evento(int id, String titulo, LocalDateTime data, String categoria, int ingressosDisponiveis, int ingressosVendidos, String imagem, double preco){
        this.setId(id);
        this.setTitulo(titulo);
        this.setData(data);
        this.setCategoria(categoria);
        this.setIngressosDisponiveis(ingressosDisponiveis);
        this.setIngressosVendidos(ingressosVendidos);
        this.setImagem(imagem);
        this.setPreco(preco);
    }
    public int getId(){return id;}
    public void setId(int id){
        if(id<0){
            throw new IllegalArgumentException("Id inválido");
        }
        this.id = id;
    }
    public String getTitulo(){return titulo;}
    public void setTitulo(String titulo){
        if(titulo == null){
            throw new IllegalArgumentException("Evento sem nome");
        }
        this.titulo = titulo;
    }

    public LocalDateTime getData(){return data;}
    public void setData(LocalDateTime data){
        if(data == null){
            throw new IllegalArgumentException("Evento sem data");
        }
        this.data = data;
    }
    public String getCategoria(){return categoria;}
    public void setCategoria(String categoria){
        if(categoria == null){
            throw new IllegalArgumentException("Categoria indefinida");
        }
        this.categoria = categoria;
    }
    public int getIngressosDisponiveis(){return ingressosDisponiveis;}
    public void setIngressosDisponiveis(int ingressosDisponiveis){
        if(ingressosDisponiveis < ingressosVendidos){
            throw new IllegalArgumentException("A capacidade total não pode ser menor que os ingressos já vendidos!");
        }
        this.ingressosDisponiveis = ingressosDisponiveis;
    }
    public int getIngressosVendidos(){return ingressosVendidos;}
    public void setIngressosVendidos(int ingressosVendidos){
        if(ingressosVendidos <0 || ingressosVendidos > ingressosDisponiveis){
            throw new IllegalArgumentException("Ingressos esgotados!");
        }
        this.ingressosVendidos = ingressosVendidos;
    }
    public String getImagem(){return imagem;}
    public void setImagem(String imagem){
        if(imagem == null){
            throw new IllegalArgumentException("Imagem indisponível");
        }
        this.imagem = imagem;
    }
    public double getPreco(){return preco;}
    public void setPreco(double preco){
        if(preco <0){
            throw new IllegalArgumentException("Preço inválido");
        }
        this.preco = preco;
    }
}
