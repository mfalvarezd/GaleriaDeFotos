package com.proyecto.galeriadefotos;

import com.proyecto.modelo.Persona;
import com.proyecto.modelo.Usuario;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class PrimaryController {

 
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
    private Label lblMsg;
    @FXML
    private TextField tfusuario;
    @FXML
    private TextField tfContra;
    static Usuario usuarioLogeado;
    @FXML
    private AnchorPane paneR;
    @FXML
    private Button btnAcceder;
    @FXML
    private BorderPane paneP;

    private boolean logIn(String user, String password) {
        for (Usuario u : getUsuarios()) {
            if (u.getUsuario().equals(user) && u.getPassword().equals(password)) {
                usuarioLogeado = u;
                return true;
            }
        }
        return false;

    }

    @FXML
    private void Salir(ActionEvent event) {
        System.exit(0);
    }

    private ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            BufferedReader bf = new BufferedReader(new FileReader("datos/Usuarios.csv"));
            bf.readLine();
            String line;
            while ((line = bf.readLine()) != null) {
                String[] datos = line.split(",");
                Persona p = new Persona(datos[2]);
                usuarios.add(new Usuario(datos[0], datos[1],p));
            }
            bf.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return usuarios;
    }

    private boolean verificarUsuario(String usuario) {
        for (Usuario u : getUsuarios()) {
            if (usuario.equals(u.getUsuario())) {
                
                return true;
            }

        }
        return false;
    }

    private void agregarUsuario(String usuario, String password,String nombre) {
        try {
            FileWriter fw = new FileWriter("datos/Usuarios.csv", true);

            fw.write(usuario + "," + password+","+nombre);
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void registrarse(ActionEvent event) {

        pContenido.getChildren().clear();
        paneR.setMinHeight(600);
        pContenido.setSpacing(10);
        buttonBox.getChildren().clear();
        Label lblRegistro = new Label("Registro");

        Label lblUsuario = new Label("Ingrese un Usuario:");
        TextField tFuser = new TextField();
        tFuser.setMaxWidth(250);

        Label lblPassword = new Label("Ingrese una Contraseña:");
        TextField tFpassword = new TextField();
        tFpassword.setMaxWidth(250);
        Label lblmr = new Label();
        lblmr.setVisible(false);
        Button btnRegistrarse = new Button("Registrarse");
        Label lblNombre = new Label("Ingrese un Nombre y Apellido");
        TextField tfNombre = new TextField();
        tfNombre.setMaxWidth(250);
        pContenido.getChildren().addAll(lblRegistro, lblUsuario, tFuser, lblPassword, tFpassword, lblNombre,tfNombre,lblmr,btnRegistrarse);
        Button btnSalir = new Button("Salir");
        Button btnVolver = new Button("Volver");
        btnRegistrarse.setOnAction(ev -> {
            if (verificarUsuario(tFuser.getText())) {
                lblmr.setVisible(true);
                lblmr.setText("El usuario ya existe, intente otro nombre de usuario");

            } else {
                if (tFuser.getText().isBlank() || tFpassword.getText().isBlank()) {
                    lblmr.setVisible(true);
                    lblmr.setText("Por favor, complete todos los recuadros");
                } else {
                    lblmr.setVisible(true);
                    lblmr.setText("Usuario registrado exitosamente");
                    agregarUsuario(tFuser.getText(), tFpassword.getText(),tfNombre.getText());
                }

            }

        });
        btnVolver.setOnAction(ev -> {
            try {
                App.setRoot("primary");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnSalir.setOnAction(ev -> System.exit(0));
        buttonBox.getChildren().addAll(btnSalir, btnVolver);

    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        String user = tfusuario.getText();
        String password = tfContra.getText();
        System.out.println(user);
        if (logIn(user, password)) {
            try {
                
                App.setRoot("gallery");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            lblMsg.setVisible(true);
            lblMsg.setText("Credenciales incorrectas, intente nuevamente");
        }

    }

    @FXML
    private void enter(KeyEvent event) {
        tfContra.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    String user = tfusuario.getText();
                    String password = tfContra.getText();
                    
                  
                    if (logIn(user, password)) {
                        try {
                            App.setRoot("gallery");
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        lblMsg.setVisible(true);
                        lblMsg.setText("Credenciales incorrectas, intente nuevamente");
                    }
                }
            }
        });

    }
}
