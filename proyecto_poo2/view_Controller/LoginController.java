package com.example.proyecto_poo2.view_Controller;

import com.example.proyecto_poo2.model.PlataformaService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class LoginController {

    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private Button btnIngresar;
    @FXML private Label lblMensaje;

    private PlataformaService plataformaService = PlataformaService.getInstancia();

    @FXML
    void handleIngresar(ActionEvent event) {
        String correo = txtCorreo.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (correo.isEmpty() || telefono.isEmpty()) {
            lblMensaje.setText("Por favor, rellene todos los campos de inicio de sesión.");
            return;
        }

        boolean autenticado = plataformaService.iniciarSesion(correo, telefono);

        if (autenticado) {
            lblMensaje.setStyle("-fx-text-fill: green;");
            lblMensaje.setText("¡Acceso concedido!");
            irAlDashboard();
        } else {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText("Usuario no encontrado o credenciales incorrectas.");
        }
    }

    private void irAlDashboard() {
        try {
            String rutaCompleta = "/com/example/proyecto_poo2/view/DashboardView.fxml";
            String rutaCorta = "/view/DashboardView.fxml";

            URL fxmlLocation = getClass().getResource(rutaCompleta);
            if (fxmlLocation == null) {
                fxmlLocation = getClass().getResource(rutaCorta);
            }

            if (fxmlLocation == null) {
                throw new IOException("No se encontró DashboardView.fxml en recursos.");
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            Stage stage = (Stage) btnIngresar.getScene().getWindow();
            stage.setTitle("Plataforma de Eventos - Catálogo");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();

        } catch (IOException e) {
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setText("Error crítico al cargar el menú principal.");
            e.printStackTrace();
        }
    }
}