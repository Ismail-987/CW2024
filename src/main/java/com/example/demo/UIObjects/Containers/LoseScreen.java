package com.example.demo.UIObjects.Containers;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Time;
import java.util.Objects;

public class LoseScreen {

    private Group root;

    private String image = "/com/example/demo/images/youLostScreen.jpg";
    private ImageView winBackground = new ImageView();
    private Timeline timeline;

    public LoseScreen(double Xposition, double Yposition){

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
