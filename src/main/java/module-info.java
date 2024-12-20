module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires java.sql;

    opens com.example.demo.controller to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.UIObjects.Images.actors to javafx.fxml;
    opens com.example.demo.UIObjects.Images.figures to javafx.fxml;
    opens com.example.demo.UIObjects.Containers to javafx.fxml;
    opens com.example.demo.scenes to javafx.fxml;
    opens com.example.demo.utilities.uiManagers to javafx.fxml;
}