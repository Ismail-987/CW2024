module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.UIObjects.Images.actors to javafx.fxml;
    opens com.example.demo.UIObjects.Images.figures to javafx.fxml;
    opens com.example.demo.UIObjects.Containers to javafx.fxml;
}