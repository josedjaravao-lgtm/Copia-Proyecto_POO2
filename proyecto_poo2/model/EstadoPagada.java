package com.example.proyecto_poo2.model;
// EstadoPagada.java
public class EstadoPagada implements CompraState {

    @Override
    public void pagar(Compra compra) {
        System.out.println("Error: Esta compra ya ha sido pagada previamente.");
    }

    @Override
    public void cancelar(Compra compra) {
        // Dependiendo de las políticas, se permite o no. Vamos a permitirlo como un reembolso pendiente.
        System.out.println("Solicitando reembolso... Compra cancelada.");
        compra.setEstadoActual(new EstadoCancelada());
    }
}