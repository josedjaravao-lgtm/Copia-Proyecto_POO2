package com.example.proyecto_poo2.model;

public interface PaymentAdapter {
    boolean procesarPayment(MetodoPagoSimulado metodo, double monto);
}