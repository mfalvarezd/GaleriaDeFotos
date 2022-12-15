package com.proyecto.galeriadefotos;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryController {
    @FXML
    Label lbIzq;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    @FXML
    private void cambiarTexto() throws IOException{
        String texto = lbIzq.getText();
        lbIzq.setText("Hola");
    }
}
