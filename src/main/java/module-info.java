module com.example.foldermaneger {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.management;


    opens com.example.foldermaneger to javafx.fxml;
    exports com.example.foldermaneger;
    exports com.example.foldermaneger.Controllers;
    opens com.example.foldermaneger.Controllers to javafx.fxml;
    exports com.example.foldermaneger.Methods;
    opens com.example.foldermaneger.Methods to javafx.fxml;
}