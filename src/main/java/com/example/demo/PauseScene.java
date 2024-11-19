package com.example.demo;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;


public class PauseScene {

    private Group root;

    private String image = "/com/example/demo/images/pause_background1.jpg";
    private ImageView pauseBackground = new ImageView();
    private  double SCREENHEIGHT;
    private double SCREENWIDTH;
    private double X_POSITION;
    private double Y_POSITION;

    public PauseScene (double Xposition, double Yposition){
        this.root = new Group();
        this.root.setLayoutX(Xposition);
        this.root.setLayoutY(Yposition);
        this.pauseBackground.setImage(new Image(Objects.requireNonNull(getClass().getResource(image)).toExternalForm()));

        root.getChildren().add(pauseBackground);
    }

    public Group get_scene_container(){
        return root;
    }

}
