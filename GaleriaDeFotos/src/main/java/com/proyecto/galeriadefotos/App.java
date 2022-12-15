package com.proyecto.galeriadefotos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import modelo.*;

public class App extends Application {

    private static Scene scene;
    static Usuario usuarioLogeado;

    @Override
    public void start(Stage stage) throws IOException {
        ArrayList<Persona> personas = new ArrayList<>();
        ArrayList<Usuario> usuarios = new ArrayList<>();
        
        
      
        
        
        
        
        
        
        
        
        scene = new Scene(loadFXML("primary"), 1200,720);
        stage.setScene(scene);
        stage.show();
    }
    
    public void crearAlbum(String nombre, String descripcion){
        usuarioLogeado.getGaleria().getAlbumes().add(new Album(nombre,descripcion));
    
    }
    public void mostrarAlbumes(){
        for(Album album : usuarioLogeado.getGaleria().getAlbumes()){
            System.out.println(album);
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}