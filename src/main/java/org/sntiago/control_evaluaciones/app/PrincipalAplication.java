package org.sntiago.control_evaluaciones.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class PrincipalAplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PrincipalAplication.class.getResource("/org/sntiago/control_evaluaciones/principal_view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Evaluacion de Estudiantes");
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}