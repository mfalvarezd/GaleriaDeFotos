/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.proyecto.galeriadefotos;

import com.proyecto.modelo.Album;
import com.proyecto.modelo.Usuario;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Moises Alvarez
 */
public class GalleryController implements Initializable {

    @FXML
    private Button btnImportar;
    @FXML
    private VBox panePrincipal;
    @FXML
    private VBox listaAlbum;
    @FXML
    private Label lblUser;
    static Usuario usuarioLogeado = PrimaryController.usuarioLogeado;
    @FXML
    private BorderPane bPane;
    
    
    
    
    public void cargarAlbumes(){
        for(Album a: usuarioLogeado.getGaleria().getAlbumes()){
            
            listaAlbum.getChildren().add(new Button(a.getNombre()));
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaAlbum.getChildren().clear();
        cargarAlbumes();
        ArrayList<Album> albumes = usuarioLogeado.getGaleria().getAlbumes();

        lblUser.setText("Usuario: " + usuarioLogeado.getPersona().getNombres());
        btnImportar.setGraphic(new ImageView(new Image("file:img/iconCamera.png", 20, 20, false, true)));
        btnImportar.setOnAction(ev -> {
            Alert dt = new Alert(Alert.AlertType.CONFIRMATION);
            dt.setGraphic(new ImageView(new Image("file:img/iconCamera.png", 30, 30, false, true)));
            dt.setTitle("Importar Imagenes");
            dt.setHeaderText("Importando imagenes");
            dt.setContentText("Seleccione una opcion");
            ButtonType boton1 = new ButtonType("Crear Album");
            ButtonType boton2 = new ButtonType("Añadir a Album");
            ButtonType boton3 = new ButtonType("Cancelar");
            dt.getButtonTypes().setAll(boton1, boton2, boton3);
            Optional<ButtonType> opciones = dt.showAndWait();
            if (opciones.get() == boton1) {

                Dialog dialogoPersonalizado = new Dialog();
                dialogoPersonalizado.setTitle("Creando Album");
                dialogoPersonalizado.setHeaderText("Album");
                dialogoPersonalizado.setContentText("Llene todos los campos para crear el Album");
                dialogoPersonalizado.setGraphic(new ImageView(new Image("file:img/iconCamera.png", 30, 30, false, true)));
                dialogoPersonalizado.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                TextField nombre = new TextField();
                nombre.setPromptText("Nombre Album");
                TextField descripcion = new TextField();
                descripcion.setPromptText("Descripcion");
                grid.add(new Label("Nombre"), 0, 0);
                grid.add(nombre, 1, 0);
                grid.add(new Label("Descripcion"), 0, 1);
                grid.add(descripcion, 1, 1);
                dialogoPersonalizado.getDialogPane().setContent(grid);
                Optional<ButtonType> resultado = dialogoPersonalizado.showAndWait();
                if (resultado.get() == ButtonType.APPLY) {
                    System.out.println("Pulsado aceptar");
                    usuarioLogeado.getGaleria().crearAlbum(nombre.getText(), descripcion.getText());
                    listaAlbum.getChildren().clear();
                    cargarAlbumes();
                
                    
                } else {
                    System.out.println("Pulsado cancelar");
                }
            } else if (opciones.get() == boton2) {
                List<String> listadoA = new ArrayList<>();
                for (Album a : albumes) {
                    listadoA.add(a.getNombre());
                }

                ChoiceDialog<String> dialogoEleccion = new ChoiceDialog<String>("opción 1", listadoA);
                dialogoEleccion.setTitle("Agregando Imagenes");
                dialogoEleccion.setHeaderText("Seleccione un Album");
                dialogoEleccion.setContentText("Album");
                Optional<String> texto = dialogoEleccion.showAndWait();
                if (texto.isPresent()) {
                    System.out.println(texto.get());
                }
            } else if (opciones.get() == boton3) {
                System.out.println("Pulsado botón 3");
            }

        });
        /*
        btnImportar.setOnAction(ev -> {
            
            
            TextInputDialog dt = new TextInputDialog();
            dt.setGraphic(new ImageView(new Image("file:img/iconCamera.png", 30, 30, false, true)));
            dt.setTitle("Importando imagenes");
            dt.setHeaderText("Ingresa el nombre del album donde desea guardar las fotos, si el album no existe se creara uno nuevo");
            dt.setContentText("Album:");
            dt.initStyle(StageStyle.UTILITY);

            Optional<String> rs = dt.showAndWait();

            rs.ifPresent((string) -> {
                if (string.equals("")|| string.isBlank() || string.isEmpty()) {
                    Alert dialogoError = new Alert(Alert.AlertType.ERROR);
                    dialogoError.setTitle("Error");
                    dialogoError.setHeaderText("No puede dejar el contenedor de Album vacio");
                    dialogoError.setContentText("Especificar Album, intente nuevamente");
                    dialogoError.showAndWait();
                } else {
                    FileChooser fc = new FileChooser();
                    fc.setTitle("Importar Imagenes");
                    fc.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("All Images", "*.*"),
                            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                            new FileChooser.ExtensionFilter("PNG", "*.png")
                    );

                    List<File> list = fc.showOpenMultipleDialog(null);
                    HBox hb = new HBox(10);
                    


                    for (File f : list) {
                        if (f != null) {
                            Image image = new Image("file:" + f.getAbsolutePath(), 65, 80, false, true);

                            hb.getChildren().add(new ImageView(image));

                        }

                    }
                    panePrincipal.getChildren().add(hb);

                }

            });

        });
         */

        // TODO
    }

    public void crearHbox() {

    }

    @FXML
    private void importarFotos(ActionEvent event) {

    }

}
