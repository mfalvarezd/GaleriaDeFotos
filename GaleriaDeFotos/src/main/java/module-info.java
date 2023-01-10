module com.proyecto.galeriadefotos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.proyecto.galeriadefotos to javafx.fxml;
    exports com.proyecto.galeriadefotos;
}
