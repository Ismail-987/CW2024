package com.example.demo.UIObjects.Containers;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Time;
import java.util.Objects;

public class LoseScreen {

    private Group lossScreen;

    private String image = "/com/example/demo/images/youLostScreen.jpg";
    private ImageView lossBackground = new ImageView();
    private Button lossHomeButton;
    private Button lostRestartButton;
    private Button lostSkipLevelButton;
    private Button lostTipsButton;
    private Button  lostQuitButton;

    public LoseScreen(double Xposition, double Yposition){
       creator(Xposition, Yposition);
       initializer();
    }
    public Group get_scene_container(){
        return lossScreen;
    }


    public void initializer(){

        lossScreen.getChildren().add(lossBackground);
        lossScreen.getChildren().add(lostTipsButton);
        lossScreen.getChildren().add(lossHomeButton);
        lossScreen.getChildren().add(lostRestartButton);
        lossScreen.getChildren().add(lostSkipLevelButton);
        lossScreen.getChildren().add(lostQuitButton);

    }

    public void creator(double Xposition, double Yposition){
        this.lossScreen = new Group();
        // DO SOME IN-LINE STYLING
        this.lossScreen.setLayoutX(Xposition);
        this.lossScreen.setLayoutY(Yposition);
        this.lossBackground.setImage(new Image(Objects.requireNonNull(getClass().getResource(image)).toExternalForm()));
        this.lossBackground.setFitWidth(600);
        this.lossBackground.setFitHeight(513);

        this.lostQuitButton = new Button("Quit");
        lostQuitButton.setMinWidth(179.6);
        lostQuitButton.setMinHeight(35.2);
        lostQuitButton.setLayoutX(383.6);
        lostQuitButton.setLayoutY(238);

        this.lostQuitButton.setOnMousePressed(e -> {
            System.exit(1);
        });

        this.lossHomeButton = new Button("Home");
        lossHomeButton.setMinWidth(169.6);
        lossHomeButton.setMinHeight(35.2);
        lossHomeButton.setLayoutX(198.8);
        lossHomeButton.setLayoutY(378);

        this.lossHomeButton.setOnMousePressed(e -> {
            System.exit(1);
        });

        this.lostRestartButton = new Button("Replay");
        lostRestartButton.setMinWidth(175.8);
        lostRestartButton.setMinHeight(35.2);
        lostRestartButton.setLayoutX(40);
        lostRestartButton.setLayoutY(240);

        this.lostRestartButton.setOnMousePressed(e -> {
            System.exit(1);
        });

        this.lostTipsButton = new Button("Tips");
        lostTipsButton.setMinWidth(167.6);
        lostTipsButton.setMinHeight(35.2);
        lostTipsButton.setLayoutX(196.6);
        lostTipsButton.setLayoutY(425.2);
        this.lostTipsButton.setOnMousePressed(e -> {
            System.exit(1);
        });

        this.lostSkipLevelButton = new Button("Skip level");
        lostSkipLevelButton.setMinWidth(169.6);
        lostSkipLevelButton.setMinHeight(35.2);
        lostSkipLevelButton.setLayoutX(214.2);
        lostSkipLevelButton.setLayoutY(238);
        this.lostSkipLevelButton.setOnMousePressed(e -> {
            System.exit(1);
        });

    }



}
