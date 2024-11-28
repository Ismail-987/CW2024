package com.example.demo.scenes;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import java.beans.PropertyChangeSupport;
import java.util.Objects;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class HomeScene {

    private Scene scene;
    private Group root;
    private Button sgbutton;
    private Button continueButton;
    private Button quitButton;
    private Button informationButton;
    private Button settingsButton;
    private  double SCREENHEIGHT;
    private double SCREENWIDTH;
    public ImageView background;
    private String LEVELNAME;
    private String backgroundImageName = "/com/example/demo/images/homescreenbackground2.jpg";
    private ImageView homeMenu;
    private String skybattleImageName = "/com/example/demo/images/homescreen.jpg";
    private MediaPlayer homeScreenMusic = new MediaPlayer(new Media(getClass().getResource("/com/example/demo/images/homemenuviewmusic.mp3").toString()));
    private final PropertyChangeSupport support;
    public Boolean exists = false;

    public HomeScene( double screenHeight, double screenWidth){
        this.root = new Group();
        this.SCREENHEIGHT = screenHeight;
        this.SCREENWIDTH = screenWidth;
        initializeBackground();
        initializeHomeMenu();
        initializeButtons();
        this.scene = new Scene(root,screenWidth,screenHeight);
        this.support = new PropertyChangeSupport(this);
    }
    public void initializeButtons(){
        this.sgbutton = new Button();
        this.sgbutton.setOnMousePressed(e -> {
            this.homeScreenMusic.stop();
            load_level("com.example.demo.scenes.LevelOne");
        });
        sgbutton.setLayoutX(448);
        sgbutton.setLayoutY(320);
        sgbutton.setMinHeight(75);
        sgbutton.setMinWidth(110);
        sgbutton.setStyle("-fx-background-color: transparent;");

        this.quitButton = new Button();
        quitButton.setLayoutX(743);
        quitButton.setLayoutY(320);
        quitButton.setMinHeight(75);
        quitButton.setMinWidth(110);
        quitButton.setStyle("-fx-background-color: transparent;");

        this.quitButton.setOnMousePressed(e -> {
            homeScreenMusic.stop();
            System.exit(1);
        });

        this.continueButton = new Button();
        continueButton.setLayoutX(595);
        continueButton.setLayoutY(320);
        continueButton.setMinHeight(75);
        continueButton.setMinWidth(110);
        continueButton.setStyle("-fx-background-color: transparent;");

        this.continueButton.setOnMousePressed(e -> {
            homeScreenMusic.stop();
            // READING FROM A FILE TO CHECK THE LAST LEVEL POINT.
            // Define the path to the file

            File savedStatusFile = new File("src/main/resources/gameStatus/gameStatus.txt");

            // Debugging: Check if the file exists and print the file path
            System.out.println("Looking for file at: " + savedStatusFile.getAbsolutePath());

            if (savedStatusFile.exists()) {
                System.out.println("File exists!");

                try (Scanner scanner = new Scanner(savedStatusFile)) {
                    // Read the first line from the file
                    if (scanner.hasNextLine()) {
                        String levelName = scanner.nextLine().trim(); // Clean up any spaces/newlines

                        if (!levelName.isEmpty()) {
                            LEVELNAME = levelName;
                            System.out.println("Loaded Level Name: " + LEVELNAME);
                            load_level(LEVELNAME);
                        } else {
                            System.out.println("No level found. Loading default level.");
                            load_level("com.example.demo.scenes.LevelOne");
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    throw new RuntimeException("Failed to read game status", ex);
                }
            } else {
                System.out.println("File does not exist. Loading default level.");
                load_level("com.example.demo.scenes.LevelOne");
            }

        });

        this.informationButton = new Button();
        informationButton.setLayoutX(448);
        informationButton.setLayoutY(470);
        informationButton.setMinHeight(75);
        informationButton.setMinWidth(210);
        informationButton.setStyle("-fx-background-color: transparent;");

        this.informationButton.setOnMousePressed(e -> {
            homeScreenMusic.stop();
            load_level("com.example.demo.scenes.LevelOne");
        });


        this.settingsButton = new Button();
        settingsButton.setLayoutX(663);
        settingsButton.setLayoutY(470);
        settingsButton.setMinHeight(75);
        settingsButton.setMinWidth(190);
        settingsButton.setStyle("-fx-background-color: transparent;");

        this.settingsButton.setOnMousePressed(e -> {
            homeScreenMusic.stop();
            load_level("com.example.demo.scenes.LevelOne");
        });

        root.getChildren().add(sgbutton);
        root.getChildren().add(continueButton);
        root.getChildren().add(quitButton);
        root.getChildren().add(informationButton);
        root.getChildren().add(settingsButton);
         // Add a Label (Follows absolute reference). SIMILAR TO document.createElement().

    }

    public void initializeHomeMenu(){
        this.homeMenu = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(skybattleImageName)).toExternalForm()));
        homeMenu.setFitWidth(500);
        homeMenu.setFitHeight(500);
        homeMenu.setLayoutX(400);
        homeMenu.setLayoutY(100);
        root.getChildren().add(homeMenu);
    }

    public void initializeBackground(){
        this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
        background.setFitHeight(SCREENHEIGHT);
        background.setFitWidth(SCREENWIDTH);
        root.getChildren().add(background);
    }
    public Scene returnScene(){

        return scene; // This is like A React Component Returning a Single div or container.
    }
    public void load_level(String levelName) {
        if(exists){
            support.firePropertyChange("Page Change",null, levelName); // Notify all observers with change of Level
        }
        else {
            support.firePropertyChange("Level",null, levelName); // Notify all observers with change of Level
        }

    }

    public PropertyChangeSupport getSupport(){
        return this.support;
    }

    public Group getRoot(){
        return this.root;
    }

    public MediaPlayer getHomeScreenMusic(){
        return this.homeScreenMusic;
    }

}
