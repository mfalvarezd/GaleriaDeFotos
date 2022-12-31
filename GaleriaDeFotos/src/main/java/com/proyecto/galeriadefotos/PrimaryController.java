package com.proyecto.galeriadefotos;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrimaryController {
    Label lbIzq;
    @FXML
    Button btnAcceder;
    @FXML
    private Button btnSalir;
    private TextField tFuser;
    private TextField tFpassword;
    @FXML
    private Label lblMessage;
    @FXML
    private Button btnRegistro;
    @FXML
    private VBox pContenido;
    @FXML
    private HBox buttonBox;
    @FXML
    private TextField tfUser;
    @FXML
    private TextField tfPass;
    @FXML
    private Label lblMsg;
    
    
    

    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    private void cambiarTexto() throws IOException{
        String texto = lbIzq.getText();
        lbIzq.setText("Hola");
    }

    private void Acceder(ActionEvent event) {
        String user = tFuser.getText();
        String password  = tFpassword.getText();
        
    }

    @FXML
    private void Salir(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void registrarse(ActionEvent event) {
        
        pContenido.getChildren().clear();
        pContenido.setSpacing(10);
        buttonBox.getChildren().clear();
        Label lblRegistro = new Label("Registro");
        
        Label lblUsuario =  new Label("Ingrese un Usuario:");
        TextField tFuser = new TextField();
        tFuser.setMaxWidth(250);

        Label lblPassword = new Label("Ingrese una Contraseña:");
        TextField tFpassword = new TextField();
        tFpassword.setMaxWidth(250);
        Button btnRegistrarse = new Button("Registrarse");
        pContenido.getChildren().addAll(lblRegistro,lblUsuario,tFuser,lblPassword,tFpassword,btnRegistrarse);
        Button btnSalir = new Button("Salir");
        Button btnVolver= new Button("Volver");
        btnVolver.setOnAction(ev -> {
            try {
                App.setRoot("primary");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnSalir.setOnAction(ev -> System.exit(0));
        buttonBox.getChildren().addAll(btnSalir,btnVolver);
     
        
    }
}
