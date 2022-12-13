module com.proyecto.galeriadefotos {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.proyecto.galeriadefotos to javafx.fxml;
    exports com.proyecto.galeriadefotos;
}
