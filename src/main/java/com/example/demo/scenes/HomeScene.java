package com.example.demo.scenes;

import java.beans.PropertyChangeSupport;
import java.util.Objects;

import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Represents the home scene of the game, providing navigation and settings.
 */
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
    private ImageView homeMenu;
    private MediaPlayer homeScreenMusic ;
    private final PropertyChangeSupport support;
    public Boolean exists = false;

    /**
     * Constructs the HomeScene object with specified screen dimensions.
     *
     * @param screenHeight The height of the screen.
     * @param screenWidth  The width of the screen.
     */
    public HomeScene( double screenHeight, double screenWidth){
        this.root = new Group();
        this.SCREENHEIGHT = screenHeight;
        this.SCREENWIDTH = screenWidth;
        initializeBackground();
        initializeHomeMenu();
        initializeButtons();
        this.scene = new Scene(root,screenWidth,screenHeight);
        this.support = new PropertyChangeSupport(this);
        this.homeScreenMusic = new MediaPlayer(new Media(getClass().getResource(DataUtilities.HomeMusic).toString()));
    }
    /**
     * Initializes the buttons for the home scene.
     */
    public void initializeButtons(){
        this.sgbutton = new Button();
        this.sgbutton.setOnMousePressed(e -> {
            this.homeScreenMusic.stop();
            goToScene("com.example.demo.scenes.LevelOne");
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
            goToScene(FileUtility.readGameStatus());
        });

        this.informationButton = new Button();
        informationButton.setLayoutX(448);
        informationButton.setLayoutY(470);
        informationButton.setMinHeight(75);
        informationButton.setMinWidth(210);
        informationButton.setStyle("-fx-background-color: transparent;");

        this.informationButton.setOnMousePressed(e -> {
            homeScreenMusic.stop();
            goToScene(DataUtilities.InformationScene);
        });


        this.settingsButton = new Button();
        settingsButton.setLayoutX(663);
        settingsButton.setLayoutY(470);
        settingsButton.setMinHeight(75);
        settingsButton.setMinWidth(190);
        settingsButton.setStyle("-fx-background-color: transparent;");

        this.settingsButton.setOnMousePressed(e -> {
            homeScreenMusic.stop();
            goToScene(DataUtilities.SettingsScene);
        });

        root.getChildren().add(sgbutton);
        root.getChildren().add(continueButton);
        root.getChildren().add(quitButton);
        root.getChildren().add(informationButton);
        root.getChildren().add(settingsButton);
         // Add a Label (Follows absolute reference). SIMILAR TO document.createElement().

    }

    /**
     * Sets up the home menu view.
     */
    public void initializeHomeMenu(){
        this.homeMenu = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(DataUtilities.HomeMenuBackgroundImage)).toExternalForm()));
        homeMenu.setFitWidth(500);
        homeMenu.setFitHeight(500);
        homeMenu.setLayoutX(400);
        homeMenu.setLayoutY(100);
        root.getChildren().add(homeMenu);
    }

    /**
     * Sets up the background image for the home scene.
     */
    public void initializeBackground() {
        String resourcePath = DataUtilities.HomeBackgroundImage;
        if (getClass().getResource(resourcePath) == null) {
            throw new IllegalArgumentException("Resource not found: " + resourcePath);
        }

        Image image = new Image(getClass().getResource(resourcePath).toExternalForm());
        this.background = new ImageView(image);
        background.setFitHeight(SCREENHEIGHT);
        background.setFitWidth(SCREENWIDTH);
        root.getChildren().add(background);
    }

    /**
     * Returns the scene object for display.
     *
     * @return the Scene of the home interface
     */
    public Scene returnScene(){

        return scene; // This is like A React Component Returning a Single div or container.
    }

    /**
     * Loads the level data by the given name.
     *
     * @param levelName The name of the level to load.
     */
    public void goToScene(String levelName) {
        homeScreenMusic.stop();
        support.firePropertyChange("Page Changes",null, levelName); // Notify all observers with change of Level
    }

    /**
     * Returns the PropertyChangeSupport object for managing property change listeners.
     *
     * @return the PropertyChangeSupport instance
     */
    public PropertyChangeSupport getSupport(){
        return this.support;
    }

    /**
     * Returns the root group of the home scene.
     *
     * @return the root Group of the scene
     */
    public Group getRoot(){
        return this.root;
    }

    /**
     * Returns the MediaPlayer for home screen music.
     *
     * @return the MediaPlayer of the home screen music
     */
    public MediaPlayer getHomeScreenMusic(){

        return this.homeScreenMusic;
    }

    public Button getSgbutton() {
        return sgbutton;
    }

    public Button getContinueButton() {
        return continueButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }

}
