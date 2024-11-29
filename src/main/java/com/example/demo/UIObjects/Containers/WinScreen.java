package com.example.demo.UIObjects.Containers;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Time;
import java.util.Objects;


public class WinScreen {
    private Group root;

    private String image = "/com/example/demo/images/winScreen.jpg";
    private ImageView winBackground = new ImageView();
    private Group winScreen;


    public WinScreen(double Xposition, double Yposition){

        this.root = new Group();
        // DO SOME IN-LINE STYLING
        this.root.setLayoutX(Xposition);
        this.root.setLayoutY(Yposition);
        this.winBackground.setImage(new Image(Objects.requireNonNull(getClass().getResource(image)).toExternalForm()));
        this.winBackground.setFitWidth(600);
        this.winBackground.setFitHeight(513);



        root.getChildren().add(winBackground);

    }
    public Group get_scene_container(){
        return root;
    }
}
