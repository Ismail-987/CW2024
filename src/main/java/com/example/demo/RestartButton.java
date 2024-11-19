package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class RestartButton extends ImageView {

    public static  String IMAGE_NAME = "/com/example/demo/images/restartGame.jpg";
    private static final int HEIGHT = 127;
    private static final int WIDTH = 130;
    public RestartButton(double xPosition, double yPosition) {
//		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
        this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
        this.setFitHeight(HEIGHT);
        this.setFitWidth(WIDTH);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }
}
