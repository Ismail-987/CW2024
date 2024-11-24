package com.example.demo.UIObjects.Containers;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Time;
import java.util.Objects;


public class PauseScreen {

    private Group root;

    private String image = "/com/example/demo/images/pauseScreen.jpg";
    private ImageView pauseBackground = new ImageView();
    private Timeline timeline;


    public PauseScreen (double Xposition, double Yposition){
        this.root = new Group();
        // DO SOME IN-LINE STYLING
        this.root.setLayoutX(Xposition);
        this.root.setLayoutY(Yposition);
        this.pauseBackground.setImage(new Image(Objects.requireNonNull(getClass().getResource(image)).toExternalForm()));
        this.pauseBackground.setFitWidth(600);
        this.pauseBackground.setFitHeight(513);



        root.getChildren().add(pauseBackground);
    }

    public Group get_scene_container(){
        return root;
    }

}
