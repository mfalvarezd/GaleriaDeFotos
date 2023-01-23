/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.proyecto.galeriadefotos;

import com.proyecto.modelo.Album;
import com.proyecto.modelo.Fotografia;
import com.proyecto.modelo.Galeria;
import com.proyecto.modelo.Persona;
import com.proyecto.modelo.Usuario;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;


public class GalleryController implements Initializable {

    @FXML
    private Button btnImportar;
    @FXML
    private VBox panePrincipal;
    @FXML
    private Accordion listaAlbum;
    @FXML
    private Label lblUser;

    @FXML
    private BorderPane bPane;
    @FXML
    private MenuBar barraM;
    @FXML
    private MenuItem cerrarSesion;
    private static Usuario usuarioLogeado;
    private static Galeria galeriaU;
    private static Persona usuarioNames;
    @FXML
    private Button btnPicture;
    @FXML
    private ScrollPane izqLateral;
    static Album albumSeleccionado;
    @FXML
    private HBox informacion;
    @FXML
    private Button btnBuscar;
    @FXML
    private TextField tfBusqueda;

    public void cargarAlbumes() {
        listaAlbum.getPanes().clear();
        panePrincipal.getChildren().clear();
        panePrincipal.getChildren().add(new Separator(Orientation.HORIZONTAL));

        for (Album a : usuarioLogeado.getGaleria().getAlbumes()) {
            if (a.getFotografias().size() == 0) {
                TitledPane t = new TitledPane(a.getNombre(), new Label("Imagenes disponibles"));
                listaAlbum.getPanes().add(t);
            } else {
                TitledPane t = new TitledPane(a.getNombre(), new Label("Imagenes disponibles"));

                VBox contenido = new VBox();
                for (Fotografia f : a.getFotografias()) {
                    contenido.getChildren().add(new Label(f.getNombre()));
                    System.out.println(f.getNombre());
                }
                t.setContent(contenido);

                t.setOnMouseClicked((ev) -> {
                    t.setOnMouseClicked((evento) -> {
                        panePrincipal.getChildren().clear();
                        panePrincipal.getChildren().add(new Separator(Orientation.HORIZONTAL));
                        cargarAlbumes();

                    });
                    if (a.getFotografias().size() == 0) {
                        panePrincipal.getChildren().clear();
                        panePrincipal.getChildren().add(new Separator(Orientation.HORIZONTAL));
                        panePrincipal.getChildren().add(new Label("No hay imagenes disponibles"));
                    } else {
                        panePrincipal.getChildren().clear();
                        panePrincipal.getChildren().add(new Separator(Orientation.HORIZONTAL));
                        HBox hb = new HBox(10);
                        
                        for (Fotografia f : a.getFotografias()) {
                            
                            Image img = new Image("file:" + f.getPath(), 300, 370, false, true);
                            ImageView imv = new ImageView(img);
                            imv.setOnMouseClicked((events) -> {
                                informacion.getChildren().clear();
                                informacion.setSpacing(10);
                                informacion.getChildren().addAll(new VBox(10, new Label("Nombre: " + f.getNombre(), new Label("Descripcion: " + f.getDescripcion())), new Label("Fecha: " + f.getFecha()), new Label("Lugar: " + f.getLugar())),new Label("Comentarios: "+f.getComentarios()));

                            });
                            VBox vn = new VBox(10, imv, new Label(f.getNombre()));

                            hb.getChildren().add(vn);

                        }

                        panePrincipal.getChildren().add(hb);

                    }

                });
                listaAlbum.getPanes().add(t);

            }

        }
        izqLateral.setContent(listaAlbum);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioLogeado = PrimaryController.usuarioLogeado;

        panePrincipal.getChildren().clear();
        panePrincipal.getChildren().add(new Separator(Orientation.HORIZONTAL));

        cargarAlbumes();
        ArrayList<Fotografia> coincidencia = new ArrayList<>();
        ArrayList<Album> albumC = new ArrayList<>();
        btnBuscar.setOnAction((eh) -> {
            albumC.clear();
            coincidencia.clear();
            panePrincipal.getChildren().clear();
            panePrincipal.getChildren().add(new Separator(Orientation.HORIZONTAL));
            String palabraClave = tfBusqueda.getText().toLowerCase();

            for (Album a : usuarioLogeado.getGaleria().getAlbumes()) {
                for (Fotografia f : a.getFotografias()) {
                    for (Persona p : f.getPersonaEnFoto()) {
                        if (p.getNombres().toLowerCase().equals(palabraClave)) {
                            coincidencia.add(f);
                        }
                    }

                    if (f.getNombre().toLowerCase().equals(palabraClave) || Objects.toString(f.getLugar(), "").toLowerCase().equals(palabraClave)) {
                        coincidencia.add(f);

                    }
                }
                if (a.getNombre().toLowerCase().equals(palabraClave)) {
                    albumC.add(a);

                }
            }
            for (Album a : albumC) {

                listaAlbum.getPanes().clear();
                panePrincipal.getChildren().clear();
                panePrincipal.getChildren().add(new Separator(Orientation.HORIZONTAL));
                if (a.getFotografias().size() == 0) {
                    TitledPane t = new TitledPane(a.getNombre(), new Label("Imagenes disponibles"));
                    listaAlbum.getPanes().add(t);
                } else {
                    TitledPane t = new TitledPane(a.getNombre(), new Label("Imagenes disponibles"));

                    VBox contenido = new VBox();
                    for (Fotografia f : a.getFotografias()) {
                        contenido.getChildren().add(new Label(f.getNombre()));
                        System.out.println(f.getNombre());
                    }
                    t.setContent(contenido);

                    t.setOnMouseClicked((ev) -> {
                        t.setOnMouseClicked((evento) -> {
                            panePrincipal.getChildren().clear();
                            panePrincipal.getChildren().add(new Separator(Orientation.HORIZONTAL));
                            cargarAlbumes();

                        });
                        if (a.getFotografias().size() == 0) {
                            panePrincipal.getChildren().clear();
                            panePrincipal.getChildren().add(new Separator(Orientation.HORIZONTAL));
                            panePrincipal.getChildren().add(new Label("No hay imagenes disponibles"));
                        } else {
                            panePrincipal.getChildren().clear();
                            panePrincipal.getChildren().add(new Separator(Orientation.HORIZONTAL));
                            HBox hb = new HBox(10);
                            for (Fotografia f : a.getFotografias()) {
                                Image img = new Image("file:" + f.getPath(), 300, 370, false, true);
                                ImageView imv = new ImageView(img);
                                imv.setOnMouseClicked((events) -> {
                                    informacion.getChildren().clear();
                                    informacion.setSpacing(10);
                                    informacion.getChildren().addAll(new VBox(10, new Label("Nombre: " + f.getNombre(), new Label("Descripcion: " + f.getDescripcion())), new Label("Fecha: " + f.getFecha()), new Label("Lugar: " + f.getLugar())));

                                });
                                hb.getChildren().add(new VBox(10, imv, new Label(f.getNombre())));

                            }
                            panePrincipal.getChildren().add(hb);

                        }

                    });
                    listaAlbum.getPanes().add(t);

                }

            }

            HBox hb = new HBox(10);
            for (Fotografia f : coincidencia) {
                Image img = new Image("file:" + f.getPath(), 300, 370, false, true);
                ImageView imv = new ImageView(img);
                imv.setOnMouseClicked((events) -> {
                    informacion.getChildren().clear();
                    informacion.setSpacing(10);
                    informacion.getChildren().addAll(new VBox(10, new Label("Nombre: " + f.getNombre(), new Label("Descripcion: " + f.getDescripcion())), new Label("Fecha: " + f.getFecha()), new Label("Lugar: " + f.getLugar())));

                });
                hb.getChildren().add(new VBox(10, imv, new Label(f.getNombre())));

            }
            panePrincipal.getChildren().add(hb);

        }
        );
        ArrayList<Album> albumes = usuarioLogeado.getGaleria().getAlbumes();

        lblUser.setText("Usuario: " + usuarioLogeado.getPersona().getNombres());
        btnImportar.setGraphic(new ImageView(new Image("file:img/iconCamera.png", 20, 20, false, true)));
        //PARTE DE BOTON IMPORTAR
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
                //PARTE DE CREAR ALBUM

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
                    if (nombre.getText().equals("") || descripcion.getText().equals("")) {
                        Alert dialogoError = new Alert(Alert.AlertType.ERROR);
                        dialogoError.setTitle("Error");
                        dialogoError.setHeaderText("No puede dejar el contenedor de Album vacio");
                        dialogoError.setContentText("Especificar Album, intente nuevamente");
                        dialogoError.showAndWait();
                    } else {
                        System.out.println("Pulsado aceptar");
                        usuarioLogeado.getGaleria().crearAlbum(nombre.getText(), descripcion.getText());
                        listaAlbum.getPanes().clear();
                        cargarAlbumes();
                    }

                } else {
                    System.out.println("Pulsado cancelar");
                }
            } else if (opciones.get() == boton2) {
                //PARTE DE ANADIR IMAGENES A UN ALBUM
                List<String> listadoA = new ArrayList<>();
                for (Album a : albumes) {
                    listadoA.add(a.getNombre());
                }

                ChoiceDialog<String> dialogoEleccion = new ChoiceDialog<String>("Lista de Albumes", listadoA);
                dialogoEleccion.setTitle("Agregando Imagenes");
                dialogoEleccion.setHeaderText("Seleccione un Album");
                dialogoEleccion.setContentText("Album");
                Optional<String> texto = dialogoEleccion.showAndWait();
                texto.ifPresent((string) -> {
                    if (string.equals("") || string.isBlank() || string.isEmpty()) {
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
                        //HBox hb = new HBox(10);

                        for (File f : list) {
                            if (f != null) {
                                //Image image = new Image("file:" + f.getAbsolutePath(), 65, 80, false, true);
                                //MANDAMOS LA FOTOGRAFIA AL ALBUM
                                fotoAlbum(string, f);

                                //hb.getChildren().add(new VBox(10, new ImageView(image), new Label(f.getName())));
                            }

                        }
                        //ACTUALIZO
                        cargarAlbumes();
                        //panePrincipal.getChildren().add(hb);

                    }

                });
            } else if (opciones.get() == boton3) {
                System.out.println("Pulsado botón 3");
            }

        });

        // TODO
    }

 

    private void fotoAlbum(String album, File f) {
        for (Album a : usuarioLogeado.getGaleria().getAlbumes()) {
            if (a.getNombre().equals(album)) {

                a.anadirFotografias(new Fotografia(a, f.getAbsolutePath(), f.getName()));

            }
        }
    }

    @FXML
    private void importarFotos(ActionEvent event) {

    }

    public static void guardarDatos() {
        try {

            FileOutputStream fout = new FileOutputStream("datos/" + usuarioLogeado.getUsuario() + ".ser");
            ObjectOutputStream obj = new ObjectOutputStream(fout);
            obj.writeObject(usuarioLogeado);

            obj.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void logOut(ActionEvent event) {

        try {
            guardarDatos();
            listaAlbum.getPanes().clear();
            panePrincipal.getChildren().clear();
            App.setRoot("primary");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void editarImg(ActionEvent event) {

        panePrincipal.getChildren().clear();
        panePrincipal.getChildren().add(new Separator(Orientation.HORIZONTAL));
        VBox vb = new VBox(10);
        ChoiceBox cb = new ChoiceBox();

        ObservableList<String> personas = FXCollections.observableArrayList();
        for (Persona p : getPersonas()) {
            personas.add(p.getNombres());
        }

        for (Album a : usuarioLogeado.getGaleria().getAlbumes()) {
            cb.getItems().add(a.getNombre());
            if (a.getFotografias().size() == 0) {
                vb.getChildren().add(new Label(a.getNombre()));
                vb.getChildren().add(new Label("No hay imagen disponibles"));

            } else {
                vb.getChildren().add(new Label(a.getNombre()));

                VBox contenido = new VBox();
                for (Fotografia f : a.getFotografias()) {

                    Button bt = new Button(f.getNombre());
                    bt.setOnAction((ev) -> {

                        panePrincipal.getChildren().clear();
                        panePrincipal.getChildren().add(new Separator(Orientation.HORIZONTAL));
                        HBox hb = new HBox(10);

                        Image img = new Image("file:" + f.getPath(), 400, 450, false, true);

                        VBox nv = new VBox(10);
                        Label lblN = new Label("Imagen" + f.getNombre());
                        /*
                         private Album album;
    private String descripcion;
    private String lugar;
    private String fecha;
    private String path;
    private String nombre;
    private ArrayList<Persona> personaEnFoto;*/
                        HBox h1 = new HBox(5);
                        TextField nombre = new TextField(f.getNombre());
                        nombre.setEditable(false);
                        h1.getChildren().addAll(new Label("Nombre:test"), nombre);
                        HBox h2 = new HBox(5);
                        TextArea descripcion = new TextArea(f.getDescripcion());
                        //TextField desc = new TextField(f.getDescripcion());
                        Label lblD = new Label("Descripcion:");
                        h2.getChildren().add(descripcion);
                        HBox h3 = new HBox(5);
                        TextField lugar = new TextField(f.getLugar());
                        h3.getChildren().addAll(new Label("Lugar:"), lugar);
                        TextField fecha = new TextField(f.getFecha());
                        HBox h4 = new HBox(5);
                        Label lblFecha = new Label("dd/mm/yyyy");
                        h4.getChildren().addAll(new Label("Fecha: "), fecha, lblFecha);

                        HBox h5 = new HBox(5);
                        h5.getChildren().addAll(new Label("Album: "), new Label(f.getAlbum().getNombre()));
                        ComboBox cbP = new ComboBox();
                        cbP.setItems(personas);

                        HBox h6 = new HBox(5);
                        h6.getChildren().addAll(new Label("Etiquetar personas: "), cbP);
                        Label lblP = new Label("Personas etiquetadas en la foto:");
                        HBox h7 = new HBox(5);
                        VBox v1 = new VBox(5);
                        if (f.getPersonaEnFoto().size() == 0) {
                            v1.getChildren().add(new Label("0"));
                        } else {
                            for (Persona p : f.getPersonaEnFoto()) {

                                v1.getChildren().add(new Label(p.getNombres()));

                            }

                        }

                        h7.getChildren().addAll(lblP, v1);
                        HBox h9 = new HBox(7);
                        TextArea txtArea = new TextArea(f.getComentarios());
                        h9.getChildren().add(txtArea);
                        HBox h8 = new HBox(7);
                        Button guardar = new Button("Guardar cambios");
                        
                        HBox h10 = new HBox(7);
                        Button btnEliminar = new Button("Eliminar Fotografia, esta accion no se puede deshacer");
                        btnEliminar.setOnAction((eh)->{
                            f.getAlbum().eliminarImagen(f);
                            editarImg(event);
                        
                        
                        });
                        h10.getChildren().add(btnEliminar);
                        guardar.setOnAction((evento) -> {
                            if (descripcion.getText() != null) {
                                f.setDescripcion(descripcion.getText());
                            }

                            if (fecha.getText() != null) {
                                f.setFecha(fecha.getText());
                            }
                            if (lugar.getText() != null) {

                                f.setLugar(lugar.getText());
                            }

                            if (cbP.getValue() != null) {
                                etiquetarPersona(cbP.getValue().toString(), f);
                            }
                            if (cb.getValue() != null) {
                                moverFotografia(f, (String) cb.getValue(), f.getAlbum());
                            }
                            if(txtArea.getText()!=null){
                                f.setComentarios(txtArea.getText());
                            }
                            editarImg(event);

                        });
                        h8.getChildren().add(guardar);
                        

                        nv.getChildren().addAll(new ImageView(img), lblN, h1, lblD, h2, h3, h4, h5, new Label("Cambiar de lugar de album"), cb, h7, h6,new Label("Comentarios"),h9, h8,h10);
                        hb.getChildren().add(nv);

                        panePrincipal.getChildren().add(hb);

                    });
                    vb.getChildren().add(bt);

                }

            }

        }

        izqLateral.setContent(vb);

    }

    private void moverFotografia(Fotografia f, String album, Album pertenece) {
        Album alb = null;
        boolean existe = false;

        for (Album a : usuarioLogeado.getGaleria().getAlbumes()) {
            if (a.getNombre().equals(album)) {
                alb = a;

            }
        }
        for (Fotografia foto : alb.getFotografias()) {
            if (foto.equals(f)) {
                existe = true;
            }
        }
        if (alb != null && existe == false) {
            pertenece.eliminarImagen(f);
            f.setAlbum(alb);
            alb.anadirFotografias(f);
        }

    }

    private void etiquetarPersona(String nombres, Fotografia f) {
        boolean existe = false;
        Persona persona = null;
        for (Persona p : getPersonas()) {
            if (p.getNombres().equals(nombres)) {
                persona = p;
            }
        }
        for (Persona j : f.getPersonaEnFoto()) {
            if (persona.getNombres().equals(j.getNombres())) {
                existe = true;
            } else {
                System.out.println("Aun no anadido");
            }
        }
        if (existe == false) {
            f.anadirPersonas(persona);
        }

    }

    @FXML
    private void collections(ActionEvent event) {
        cargarAlbumes();
        for (Album a : usuarioLogeado.getGaleria().getAlbumes()) {
            for (Fotografia f : a.getFotografias()) {
                System.out.println(f.getNombre());
            }
        }
    }

    @FXML
    private void anadirPersonas(ActionEvent event) {

        Dialog dialogoPersonalizado = new Dialog();
        dialogoPersonalizado.setTitle("Añadir Persona");
        dialogoPersonalizado.setHeaderText("Persona");
        dialogoPersonalizado.setContentText("Llene todos los campos");
        dialogoPersonalizado.setGraphic(new ImageView(new Image("file:img/iconCamera.png", 30, 30, false, true)));
        dialogoPersonalizado.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        TextField nombre = new TextField();
        nombre.setPromptText("Nombres");
        TextField apellidos = new TextField();
        apellidos.setPromptText("Apellidos");
        grid.add(new Label("Nombre"), 0, 0);
        grid.add(nombre, 1, 0);
        grid.add(new Label("Apellidos"), 0, 1);
        grid.add(apellidos, 1, 1);
        dialogoPersonalizado.getDialogPane().setContent(grid);
        Optional<ButtonType> resultado = dialogoPersonalizado.showAndWait();

        if (resultado.get() == ButtonType.APPLY) {
            if (nombre.getText().equals("") || apellidos.getText().equals("")) {
                Alert dialogoError = new Alert(Alert.AlertType.ERROR);
                dialogoError.setTitle("Error");
                dialogoError.setHeaderText("No puede dejar el contenedor de Album vacio");
                dialogoError.setContentText("Especificar Album, intente nuevamente");
                dialogoError.showAndWait();
            } else {
                System.out.println("Pulsado aceptar");
                guardarPersona(nombre.getText(), apellidos.getText());

            }
        }
    }

    private void guardarPersona(String nombres, String apellidos) {
        try {

            FileOutputStream fout = new FileOutputStream("personas/" + nombres + apellidos + ".ser");
            ObjectOutputStream obj = new ObjectOutputStream(fout);
            obj.writeObject(new Persona(nombres + " " + apellidos));

            obj.flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private ArrayList<Persona> getPersonas() {
        ArrayList<Persona> personas = new ArrayList<>();
        final File carpeta = new File("personas");
        for (final File ficheroEntrada : carpeta.listFiles()) {

            try {
                ObjectInputStream ob = new ObjectInputStream(new FileInputStream(ficheroEntrada));
                personas.add((Persona) ob.readObject());
                ob.close();
            } catch (Exception ex) {

                ex.printStackTrace();
            }

        }
        return personas;
    }

    @FXML
    private void slideShow(ActionEvent event) throws IOException {

        ArrayList<Album> albumes = usuarioLogeado.getGaleria().getAlbumes();

        List<String> listadoA = new ArrayList<>();
        for (Album a : albumes) {
            listadoA.add(a.getNombre());

        }

        ChoiceDialog<String> dialogoEleccion = new ChoiceDialog<String>("Lista de Albumes", listadoA);
        dialogoEleccion.setTitle("Reproductor de Imagenes");
        dialogoEleccion.setHeaderText("Seleccione un Album a reproducir");
        dialogoEleccion.setContentText("Album");
        Optional<String> texto = dialogoEleccion.showAndWait();
        texto.ifPresent((string) -> {
            if (string.equals("") || string.isBlank() || string.isEmpty()) {
                Alert dialogoError = new Alert(Alert.AlertType.ERROR);
                dialogoError.setTitle("Error");
                dialogoError.setHeaderText("No puede dejar el contenedor de Album vacio");
                dialogoError.setContentText("Especificar Album, intente nuevamente");
                dialogoError.showAndWait();
            } else {

                albumSeleccionado = seleccionarAlbum(string);
                try {

                    App.setRoot("secondary");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }

        });

    }

    private Album seleccionarAlbum(String album) {
        Album albumSelected = null;
        for (Album a : usuarioLogeado.getGaleria().getAlbumes()) {
            System.out.println(a.getNombre());
            if (a.getNombre().equals(album)) {

                albumSelected = a;
            }

        }
        return albumSelected;
    }

}
