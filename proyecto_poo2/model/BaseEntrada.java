package com.example.proyecto_poo2.model;
// BaseEntrada.java (La entrada normalita, sin ningún extra)
public class BaseEntrada implements EntradaComponent {
    private String idEntrada;
    private Zona zona;
    private Asiento asiento;

    public BaseEntrada(String idEntrada, Zona zona, Asiento asiento) {
        this.idEntrada = idEntrada;
        this.zona = zona;
        this.asiento = asiento;
    }

    @Override
    public double getPrecioFinal() {
        // Toma el precio de la zona (que ya incluye el cálculo de la estrategia de arriba)
        return zona.getPrecioCalculado();
    }

    @Override
    public String getDetalles() {
        return "Entrada en " + zona.getNombre() + " (Asiento: " + asiento.getNumero() + ")";
    }
}