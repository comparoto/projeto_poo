package com.conectaarena.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ingressos")
public class Ingresso{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "comprador_id")
    private Usuario comprador;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    private LocalDateTime dataCompra;
    private String qrCode;

    public Ingresso(){} // lembrando que o hibernate precisa desse construtor vazio

    public Ingresso(int id, Usuario comprador, Evento evento,LocalDateTime dataCompra, String qrCode){
        this.setId(id);
        this.setComprador(comprador);
        this.setEvento(evento);
        this.setQrCode(qrCode);
        this.setDataCompra(dataCompra);
    }
    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public Usuario getComprador(){return comprador;}
    public void setComprador(Usuario comprador){this.comprador = comprador;}

    public Evento getEvento(){return evento;}
    public void setEvento(Evento evento){this.evento = evento;}

    public String getQrCode(){return qrCode;}
    public void setQrCode(String qrCode){this.qrCode = qrCode;}

    public LocalDateTime getDataCompra(){return dataCompra;}
    public void setDataCompra(LocalDateTime dataCompra){this.dataCompra = dataCompra;}
}