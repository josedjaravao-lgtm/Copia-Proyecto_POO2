package com.example.proyecto_poo2.view_Controller;

import com.example.proyecto_poo2.model.Evento;
import com.example.proyecto_poo2.model.PlataformaService;
import com.example.proyecto_poo2.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class DashboardController {

    @FXML private Label lblBienvenida;
    @FXML private ListView<Evento> lvEventos;
    @FXML private Button btnSiguiente;
    @FXML private Label lblError;

    private PlataformaService plataformaService = PlataformaService.getInstancia();

    // Variable estática necesaria para compartir el evento seleccionado con CompraController
    private static Evento eventoSeleccionado;

    public static Evento getEventoSeleccionado() {
        return eventoSeleccionado;
    }

    @FXML
    public void initialize() {
        Usuario usuarioLogueado = plataformaService.getUsuarioAutenticado();
        if (usuarioLogueado != null) {
            lblBienvenida.setText("Bienvenido, " + usuarioLogueado.getNombreCompleto());
        }

        ObservableList<Evento> items = FXCollections.observableArrayList(plataformaService.getEventos());
        lvEventos.setItems(items);
    }

    @FXML
    void handleSeleccionarEvento(ActionEvent event) {
        Evento seleccionado = lvEventos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            if (lblError != null) {
                lblError.setText("Debe seleccionar un evento de la lista para continuar.");
            }
            return;
        }

        eventoSeleccionado = seleccionado;
        irAPantallaCompra();
    }

    private void irAPantallaCompra() {
        try {
            // Intenta buscar en ambas rutas posibles para evitar caídas
            String rutaCompleta = "/com/example/proyecto_poo2/view/CompraView.fxml";
            String rutaCorta = "/view/CompraView.fxml";

            URL fxmlLocation = getClass().getResource(rutaCompleta);
            if (fxmlLocation == null) {
                fxmlLocation = getClass().getResource(rutaCorta);
            }

            if (fxmlLocation == null) {
                throw new IOException("No se encontró CompraView.fxml en los recursos del proyecto.");
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            // Determinar el Stage actual de manera segura
            Stage stage = null;
            if (btnSiguiente != null && btnSiguiente.getScene() != null) {
                stage = (Stage) btnSiguiente.getScene().getWindow();
            } else if (lvEventos != null && lvEventos.getScene() != null) {
                stage = (Stage) lvEventos.getScene().getWindow();
            }

            if (stage != null) {
                stage.setTitle("Procesar Reserva y Adicionales");
                stage.setScene(new Scene(root, 600, 400));
                stage.show();
            }

        } catch (IOException e) {
            if (lblError != null) {
                lblError.setText("Error al cambiar a la pantalla de compra.");
            }
            e.printStackTrace();
        }
    }
}