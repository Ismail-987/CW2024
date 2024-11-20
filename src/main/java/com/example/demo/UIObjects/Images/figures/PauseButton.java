package com.example.demo.UIObjects.Images.figures;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class PauseButton extends ImageView{

    public static  String IMAGE_NAME = "/com/example/demo/images/pauseButton.jpg";
    private static final int HEIGHT = 40;
    private static final int WIDTH = 40;
    public PauseButton(double xPosition, double yPosition) {
//		this.setImage(new Image(getClass().getResource(IMAGE_NAME).toExternalForm()));
        this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
        this.setFitHeight(HEIGHT);
        this.setFitWidth(WIDTH);
        this.setLayoutX(xPosition);
        this.setLayoutY(yPosition);
    }
}