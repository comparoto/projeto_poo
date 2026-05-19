package com.conectaarena.model;

import jakarta.persistence.*;

@Entity
@Table(name = "agendamento")
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private String dataVisita;

    @Column(nullable = false)
    private String horario;

    @Column(nullable = false)
    private int quantidadePessoas;

    public Agendamento(){}

    public Agendamento(int id, Usuario usuario, String dataVisita, String horario, int quantidadePessoas){
        this.id = id;
        this.usuario = usuario;
        this.dataVisita = dataVisita;
        this.horario = horario;
        this.quantidadePessoas = quantidadePessoas;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getDataVisita() { return dataVisita; }
    public void setDataVisita(String dataVisita) { this.dataVisita = dataVisita; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public int getQuantidadePessoas() { return quantidadePessoas; }
    public void setQuantidadePessoas(int quantidadePessoas) { this.quantidadePessoas = quantidadePessoas; }
}