package com.example.demo;

import java.beans.PropertyChangeSupport;
import java.util.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.scene.Group;

public class HomeScreen {
    private Scene scene;
    private Group root;
    private Button sgbutton;
    private Label homelabel;
    private  double SCREENHEIGHT;
    private double SCREENWIDTH;
    private final PropertyChangeSupport support;

    public HomeScreen( double screenHeight, double screenWidth){
        this.root = new Group();
        this.homelabel = new Label("WELCOME TO OUR GAME");
        this.SCREENHEIGHT = screenHeight;
        this.SCREENWIDTH = screenWidth;
        init_Button();
        this.scene = new Scene(root,screenWidth,screenHeight);
        this.support = new PropertyChangeSupport(this);

    }
    public void init_Button(){
        this.sgbutton = new Button("START GAME");
        this.sgbutton.setOnMousePressed(e -> {
            load_level_1("com.example.demo.LevelOne");
        });
        sgbutton.setLayoutX(5);
        sgbutton.setLayoutY(5);
        homelabel.setLayoutX(750);
        homelabel.setLayoutY(5);

        root.getChildren().add(sgbutton);
        root.getChildren().add(homelabel); // Add a Label (Follows absolute reference). SIMILAR TO document.createElement().

    }
    public Scene init_home_scene(){

        return scene; // This is like A React Component Returning a Single div or container.
    }
    public void load_level_1(String levelName) {

        support.firePropertyChange("Page Change", null, levelName); // Notify all observers with change of Level
    }

    public PropertyChangeSupport getSupport(){
        return this.support;
    }

//    public void load_level_1(String levelName) {
//        setChanged();
//        notifyObservers(levelName); // Notify all observers with change of Level
//    }
}
