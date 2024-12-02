package com.example.demo.UIObjects.Images.figures;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class GameBackground extends ImageView {

    public GameBackground(double xPosition, double yPosition,String imageName) {
        this.setImage(new Image(Objects.requireNonNull(getClass().getResource(imageName)).toExternalForm()));
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }
}
