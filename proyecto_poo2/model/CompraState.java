package com.example.proyecto_poo2.model;
// CompraState.java
public interface CompraState {
    void pagar(Compra compra);
    void cancelar(Compra compra);
}