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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class PrimaryController implements Initializable{

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
    private TextField tfContra;
    static Usuario usuarioLogeado;
    @FXML
    private AnchorPane paneR;
    @FXML
    private Button btnAcceder;
    @FXML
    private BorderPane paneP;
    @FXML
    private PasswordField pwField;
    private ArrayList<Usuario> usuarios;

    private boolean logIn(String user, String password) {
        try {
            Usuario u = getDatos(user);
            if (u.getUsuario().equals(user) && u.getPassword().equals(password)) {
                usuarioLogeado = u;
                return true;
            }
        }catch(Exception ex){
            return false;
        }

        return false;

    }

    @FXML
    private void Salir(ActionEvent event) {
        System.exit(0);
    }

    private Usuario getDatos(String user) {

        Usuario usuarioN = null;
        final File carpeta = new File("datos");
        for (final File ficheroEntrada : carpeta.listFiles()) {

            if (ficheroEntrada.getName().equals(user + ".ser")) {
                try {
                    ObjectInputStream ob = new ObjectInputStream(new FileInputStream("datos/" + user + ".ser"));
                    usuarioN = (Usuario) ob.readObject();
                    ob.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return usuarioN;
    }

    private ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        final File carpeta = new File("datos");
        for (final File ficheroEntrada : carpeta.listFiles()) {

            try {
                ObjectInputStream ob = new ObjectInputStream(new FileInputStream(ficheroEntrada));
                usuarios.add((Usuario) ob.readObject());
                ob.close();
            } catch (Exception ex) {

                ex.printStackTrace();
            }

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

    private void agregarUsuario(String usuario, String password, String nombre) {
        try {
            
            FileOutputStream fout = new FileOutputStream("datos/" + usuario + ".ser");
            FileOutputStream fp = new FileOutputStream("personas/"+nombre+".ser");
            ObjectOutputStream obj = new ObjectOutputStream(fout);
            ObjectOutputStream obj2 = new ObjectOutputStream(fp);
            obj.writeObject(new Usuario(usuario, password, new Persona(nombre)));
            obj2.writeObject(new Persona(nombre));
            
  
            obj.flush();

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
        PasswordField pw = new PasswordField();
        pw.setMaxWidth(250);
        Label lblpw = new Label("Ingrese nuevamente la Contraseña");
        PasswordField pw2 = new PasswordField();
        pw2.setMaxWidth(250);
        Label lblmr = new Label();
        lblmr.setVisible(false);
        Button btnRegistrarse = new Button("Registrarse");
        Label lblNombre = new Label("Ingrese un Nombre y Apellido");
        TextField tfNombre = new TextField();
        tfNombre.setMaxWidth(250);
        pContenido.getChildren().addAll(lblRegistro, lblUsuario, tFuser, lblPassword, pw, lblpw, pw2, lblNombre, tfNombre, lblmr, btnRegistrarse);
        Button btnSalir = new Button("Salir");
        Button btnVolver = new Button("Volver");
        btnRegistrarse.setOnAction(ev -> {
            if (verificarUsuario(tFuser.getText())) {
                lblmr.setVisible(true);
                lblmr.setText("El usuario ya existe, intente otro nombre de usuario");

            } else {
                if (tFuser.getText().isBlank() || pw.getText().isBlank()) {
                    lblmr.setVisible(true);
                    lblmr.setText("Por favor, complete todos los recuadros");
                } else {
                    if (pw.getText().equals(pw2.getText())) {
                        lblmr.setVisible(true);
                        lblmr.setText("Usuario registrado exitosamente");
                        agregarUsuario(tFuser.getText(), pw.getText(), tfNombre.getText());

                    } else {
                        lblmr.setVisible(true);
                        lblmr.setText("Las contraseñas no coinciden");

                    }

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
        String password = pwField.getText();
        System.out.println(user);
        if (logIn(user, password)) {
            try {
                System.out.println(getDatos(user).getPersona().getNombres());
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
        pwField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    String user = tfusuario.getText();
                    String password = pwField.getText();

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    
    }
}
