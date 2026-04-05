package com.conectaarena.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ingressos")
public class Ingresso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario comprador;

    @ManyToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;

    private LocalDateTime dataCompra;
    private String qrCode;

    public Ingresso() {}

    public Ingresso(Usuario comprador, Evento evento, String qrCode) {
        this.comprador = comprador;
        this.evento = evento;
        this.qrCode = qrCode;
        this.dataCompra = LocalDateTime.now();
    }

    // getters
    public Usuario getComprador() { return comprador; }
    public Evento getEvento() { return evento; }
    public String getQrCode() { return qrCode; }
}