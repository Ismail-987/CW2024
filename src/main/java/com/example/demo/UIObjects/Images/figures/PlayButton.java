package com.example.demo.UIObjects.Images.figures;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class PlayButton extends ImageView {

    public static  String IMAGE_NAME = "/com/example/demo/images/playButton1.jpg";
    private static final double HEIGHT = 52.8;
    private static final double WIDTH = 147.6;
    public PlayButton(double xPosition, double yPosition) {
        this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
        this.setFitHeight(HEIGHT);
        this.setFitWidth(WIDTH);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }
}
