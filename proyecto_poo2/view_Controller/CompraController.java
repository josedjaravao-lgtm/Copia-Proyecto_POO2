package com.example.proyecto_poo2.view_Controller;

import com.example.proyecto_poo2.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CompraController {

    @FXML private Label lblInfoEvento;
    @FXML private CheckBox chkVip;
    @FXML private CheckBox chkSeguro;
    @FXML private TextField txtTarjeta;
    @FXML private Label lblTotal;
    @FXML private Label lblResultado;
    @FXML private Button btnProcesar;

    private PlataformaService plataformaService = PlataformaService.getInstancia();
    private Evento eventoActual;
    private Zona zonaSeleccionada;
    private Asiento asientoSeleccionado;
    private double totalCalculado;

    @FXML
    public void initialize() {
        eventoActual = DashboardController.getEventoSeleccionado();

        if (eventoActual != null) {
            lblInfoEvento.setText("Evento: " + eventoActual.getNombre() + " | Ubicación: " + eventoActual.getCiudad());

            if (eventoActual.getRecinto() != null && !eventoActual.getRecinto().getZonas().isEmpty()) {
                zonaSeleccionada = eventoActual.getRecinto().getZonas().get(0);
                if (zonaSeleccionada.getAsientos() != null && !zonaSeleccionada.getAsientos().isEmpty()) {
                    asientoSeleccionado = zonaSeleccionada.getAsientos().get(0);
                }
            }
            actualizarPrecio();
        }
    }

    @FXML
    void handleCambioAdicionales(ActionEvent event) {
        actualizarPrecio();
    }

    private void actualizarPrecio() {
        if (zonaSeleccionada == null) {
            lblTotal.setText("Total a Pagar: $0.0");
            return;
        }

        EntradaComponent entrada = new BaseEntrada("ENT-TEMP", zonaSeleccionada, asientoSeleccionado);
        if (chkVip.isSelected()) entrada = new VIPDecorator(entrada, 50000.0);
        if (chkSeguro.isSelected()) entrada = new SeguroDecorator(entrada, 15000.0);

        totalCalculado = entrada.getPrecioFinal();
        lblTotal.setText("Total a Pagar: $" + totalCalculado);
    }

    @FXML
    void handleProcesarCompra(ActionEvent event) {
        String numeroTarjeta = txtTarjeta.getText().trim();
        if (numeroTarjeta.isEmpty()) {
            lblResultado.setStyle("-fx-text-fill: red;");
            lblResultado.setText("Debe ingresar un número de tarjeta de crédito válido.");
            return;
        }

        try {
            Usuario usuarioLogueado = plataformaService.getUsuarioAutenticado();

            CompraBuilder builder = new CompraBuilder();
            Compra compra = builder.setUsuario(usuarioLogueado)
                    .setEvento(eventoActual)
                    .addEntrada(zonaSeleccionada, asientoSeleccionado)
                    .build();

            EntradaComponent entradaFinal = compra.getEntradas().get(0);
            if (chkVip.isSelected()) entradaFinal = new VIPDecorator(entradaFinal, 50000.0);
            if (chkSeguro.isSelected()) entradaFinal = new SeguroDecorator(entradaFinal, 15000.0);

            compra.getEntradas().clear();
            compra.agregarEntrada(entradaFinal);

            MetodoPagoSimulado metodoPago = new MetodoPagoSimulado(numeroTarjeta);
            SimulatedPaymentGateway pasarelaExterna = new SimulatedPaymentGateway();
            PaymentAdapter adaptadorPago = new PaymentAdapterImpl(pasarelaExterna);

            if (adaptadorPago.procesarPayment(metodoPago, compra.calcularTotalCompra())) {
                compra.pagarCompra();

                NotificationService servicioNotificaciones = new NotificationService();
                servicioNotificaciones.attach(new UsuarioNotificationObserver(usuarioLogueado));
                servicioNotificaciones.notifyObservers("Pago procesado con éxito para tu compra: " + compra.getIdCompra());

                plataformaService.registrarCompra(compra);

                lblResultado.setStyle("-fx-text-fill: green;");
                lblResultado.setText("¡Compra Exitosa! El ticket fue generado y decorado correctamente.");
                btnProcesar.setDisable(true);
            } else {
                lblResultado.setStyle("-fx-text-fill: red;");
                lblResultado.setText("La transacción ha sido rechazada por la entidad bancaria.");
            }
        } catch (Exception e) {
            lblResultado.setStyle("-fx-text-fill: red;");
            lblResultado.setText("Error inesperado en el flujo de procesamiento de entradas.");
            e.printStackTrace();
        }
    }
}