package com.example.demo.scenes;

import java.beans.PropertyChangeSupport;
import java.util.Objects;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private String backgroundImageName = "/com/example/demo/images/homescreenbackground2.jpg";
    private ImageView homeMenu;
    private String skybattleImageName = "/com/example/demo/images/homescreen.jpg";
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
            load_level_1("com.example.demo.scenes.LevelOne");
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
            System.exit(1);
        });

        this.continueButton = new Button();
        continueButton.setLayoutX(595);
        continueButton.setLayoutY(320);
        continueButton.setMinHeight(75);
        continueButton.setMinWidth(110);
        continueButton.setStyle("-fx-background-color: transparent;");

        this.continueButton.setOnMousePressed(e -> {
            load_level_1("com.example.demo.scenes.LevelOne");
        });

        this.informationButton = new Button();
        informationButton.setLayoutX(448);
        informationButton.setLayoutY(470);
        informationButton.setMinHeight(75);
        informationButton.setMinWidth(210);
        informationButton.setStyle("-fx-background-color: transparent;");

        this.continueButton.setOnMousePressed(e -> {
            load_level_1("com.example.demo.scenes.LevelOne");
        });


        this.settingsButton = new Button();
        settingsButton.setLayoutX(663);
        settingsButton.setLayoutY(470);
        settingsButton.setMinHeight(75);
        settingsButton.setMinWidth(190);
        settingsButton.setStyle("-fx-background-color: transparent;");

        this.continueButton.setOnMousePressed(e -> {
            load_level_1("com.example.demo.scenes.LevelOne");
        });

        //sgbutton.setStyle("-fx-background-color: transparent;");



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
    public void load_level_1(String levelName) {
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
//    public void load_level_1(String levelName) {
//        setChanged();
//        notifyObservers(levelName); // Notify all observers with change of Level
//    }
}
