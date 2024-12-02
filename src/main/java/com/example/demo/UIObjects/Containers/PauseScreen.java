package com.example.demo.UIObjects.Containers;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Time;
import java.util.Objects;


public class PauseScreen {

    private Group pause_scene;

    private String image = "/com/example/demo/images/pauseScreen.jpg";
    private ImageView pauseBackground = new ImageView();
    private Button playButton;
    private Button saveButton;
    private Button quitButton;
    private Button informationButton;
    private Button homeButton;
    private Button settingsButton;


    public PauseScreen (double Xposition, double Yposition, Runnable resume, Runnable goToScene , Runnable saveToGameFile){
        this.pause_scene = new Group();
        // DO SOME IN-LINE STYLING
        this.pause_scene.setLayoutX(Xposition);
        this.pause_scene.setLayoutY(Yposition);
        this.pauseBackground.setImage(new Image(Objects.requireNonNull(getClass().getResource(image)).toExternalForm()));
        this.pauseBackground.setFitWidth(600);
        this.pauseBackground.setFitHeight(513);

        this.playButton = new Button();
        playButton.setMinWidth(147.6);
        playButton.setMinHeight(52.8);
        playButton.setLayoutX(47);
        playButton.setLayoutY(255.6);
        playButton.getStyleClass().add("pause-buttons");

        this.saveButton = new Button();
        saveButton.setMinWidth(147.6);
        saveButton.setMinHeight(52.8);
        saveButton.setLayoutX(224.2);
        saveButton.setLayoutY(255.6);
        saveButton.getStyleClass().add("pause-buttons");

        this.homeButton = new Button();
        homeButton.setMinWidth(147.6);
        homeButton.setMinHeight(52.8);
        homeButton.setLayoutX(224.2);
        homeButton.setLayoutY(392);
        homeButton.getStyleClass().add("pause-buttons");

        this.informationButton = new Button();
        informationButton.setMinWidth(147.6);
        informationButton.setMinHeight(52.8);
        informationButton.setLayoutX(47);
        informationButton.setLayoutY(392);
        informationButton.getStyleClass().add("pause-buttons");

        this.quitButton = new Button();
        quitButton.setMinWidth(147.6);
        quitButton.setMinHeight(52.8);
        quitButton.setLayoutX(404.2);
        quitButton.setLayoutY(392);
        quitButton.getStyleClass().add("pause-buttons");

        this.settingsButton = new Button();
        settingsButton.setMinWidth(147.6);
        settingsButton.setMinHeight(52.8);
        settingsButton.setLayoutX(404.2);
        settingsButton.setLayoutY(255.6);
        settingsButton.getStyleClass().add("pause-buttons");


        this.saveButton.setOnMousePressed(e -> {
            saveToGameFile.run();

        });
        this.playButton.setOnMousePressed(e -> {
            resume.run();
        });
        this.quitButton.setOnMousePressed(e -> {
            System.exit(1);
        });
        this.informationButton.setOnMousePressed(e -> {
            resume.run();
        });
        this.settingsButton.setOnMousePressed(e -> {
            resume.run();
        });
        this.homeButton.setOnMousePressed(e -> {
            goToScene.run();
        });

        pause_scene.getChildren().add(pauseBackground);
        pause_scene.getChildren().add(playButton);
        pause_scene.getChildren().add(saveButton);
        pause_scene.getChildren().add(homeButton);
        pause_scene.getChildren().add(informationButton);
        pause_scene.getChildren().add(settingsButton);
        pause_scene.getChildren().add(quitButton);

    }

    public Group get_scene_container(){
        return pause_scene;
    }

}
