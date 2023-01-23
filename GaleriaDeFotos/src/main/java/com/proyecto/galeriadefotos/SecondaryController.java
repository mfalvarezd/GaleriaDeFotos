package com.proyecto.galeriadefotos;

import com.proyecto.modelo.Album;
import com.proyecto.modelo.Fotografia;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SecondaryController implements Initializable {

    private static Album albumSeleccionado;

    @FXML
    private ImageView imv;
    private int cont = 0;
    @FXML
    private Button btnIzquierda;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnDerecha;
    @FXML
    private Slider velocidad;
    private static Timeline tm = new Timeline();//lo que hace la repproduccion
    KeyFrame kf;

    @FXML
    private Button btnPausa;
    private int vpr;
    ArrayList<Image> images = new ArrayList<>();
    @FXML
    private Button btnVolver;

    public void slideShow() {
        Platform.runLater(()->{
        
        for (Fotografia f : albumSeleccionado.getFotografias()) {

            images.add(new Image("file:" + f.getPath()));
        }

        tm.getKeyFrames().clear();
        kf = (new KeyFrame(javafx.util.Duration.seconds(vpr), event -> {

            imv.setImage(images.get(cont));

            cont++;
            if (cont == images.size()) {
                cont = 0;

            }

        }));

        tm.getKeyFrames().add(kf);

        tm.setCycleCount(Timeline.INDEFINITE);
        
        
        });
        

        

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        vpr = (int) velocidad.getValue();

        velocidad.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov,
                    Number olv_val, Number new_val) {
                vpr = (int) velocidad.getValue();

            }

        });

        albumSeleccionado = GalleryController.albumSeleccionado;

        slideShow();
        btnPlay.setOnAction((ev) -> {
            

            btnPlay.setDisable(true);
            tm.play();
            velocidad.setDisable(true);

        });

        btnPausa.setOnAction((evento) -> {
            tm.pause();
            velocidad.setDisable(false);
            btnPlay.setDisable(false);
        });
        btnDerecha.setOnAction((e)->{
          if (cont == images.size()) {
                cont = 0;

            }
         cont++;
         imv.setImage(images.get(cont));});
        btnIzquierda.setOnAction((eh)->{
            if (cont == images.size()) {
                cont = 0;

            }else if(cont<0){
                cont=images.size();
            }

            
            
            
            cont--;
            imv.setImage(images.get(cont));});

    }

    @FXML
    private void volver(ActionEvent event) {
        tm.stop();
        try {
            App.setRoot("gallery");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
