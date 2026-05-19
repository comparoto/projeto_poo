package com.conectaarena.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sugestoes")
public class Sugestao{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String texto;

    @Column(updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();;

    public Sugestao(){} // lembrando que o hibernate precisa desse construtor vazio

    public Sugestao(String texto){
        this.setTexto(texto);
    }
    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public String getTexto(){return texto;}
    public void setTexto(String texto){
        if(texto == null){
            throw new IllegalArgumentException("Texto em braco!");
        }
        this.texto = texto;
    }
    public LocalDateTime getDataCriacao(){return dataCriacao;}
    public void setDataCriacao(LocalDateTime dataCriacao){this.dataCriacao = dataCriacao;}
}