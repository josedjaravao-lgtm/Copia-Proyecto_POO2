package com.example.proyecto_poo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Intenta cargar el Login usando un sistema de respaldo de rutas
        String rutaCompleta = "/com/example/proyecto_poo2/view/LoginView.fxml";
        String rutaCorta = "/view/LoginView.fxml";

        URL fxmlLocation = getClass().getResource(rutaCompleta);
        if (fxmlLocation == null) {
            fxmlLocation = getClass().getResource(rutaCorta);
        }

        if (fxmlLocation == null) {
            System.err.println("¡Error Crítico! No se encontró LoginView.fxml en los recursos.");
            System.err.println("Rutas intentadas:\n 1. " + rutaCompleta + "\n 2. " + rutaCorta);
            System.exit(1);
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Parent root = loader.load();

        primaryStage.setTitle("Plataforma de Eventos - Iniciar Sesión");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}