package com.example.proyecto_poo2.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private String idCompra;
    private Usuario usuario;
    private Evento evento;
    private List<EntradaComponent> entradas;
    private LocalDateTime fechaCompra;

    public Compra(String idCompra, Usuario usuario, Evento evento) {
        this.idCompra = idCompra;
        this.usuario = usuario;
        this.evento = evento;
        this.entradas = new ArrayList<>();
        this.fechaCompra = LocalDateTime.now();
    }

    public void agregarEntrada(EntradaComponent entrada) {
        this.entradas.add(entrada);
    }

    public double calcularTotalCompra() {
        double total = 0;
        for (EntradaComponent entrada : entradas) {
            total += entrada.getPrecioFinal();
        }
        return total;
    }

    public String getIdCompra() { return idCompra; }
    public Usuario getUsuario() { return usuario; }
    public Evento getEvento() { return evento; }
    public List<EntradaComponent> getEntradas() { return entradas; }
    public LocalDateTime getFechaCompra() { return fechaCompra; }
}