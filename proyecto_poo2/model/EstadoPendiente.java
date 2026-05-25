package com.example.proyecto_poo2.model;
// EstadoPendiente.java
public class EstadoPendiente implements CompraState {

    @Override
    public void pagar(Compra compra) {
        System.out.println("Procesando pago exitosamente...");
        // Cambiamos el estado de los asientos de la compra a VENDIDOS
        compra.getEntradas().forEach(entrada -> {
            // Aquí la lógica para actualizar el estado del asiento si fuera necesario
        });
        compra.setEstadoActual(new EstadoPagada());
        System.out.println("La compra ahora está PAGADA.");
    }

    @Override
    public void cancelar(Compra compra) {
        System.out.println("Cancelando la reserva de la compra...");
        compra.setEstadoActual(new EstadoCancelada());
        System.out.println("La compra ahora está CANCELADA.");
    }
}