package com.example.demo.scenes;

import java.beans.PropertyChangeSupport;
import com.example.demo.utilities.DataUtilities;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Represents the settings scene of the game, providing navigation and settings.
 */
public class SettingsScene {

    private Scene scene;
    private Group root;
    private Button homeButton;
    private Button quitButton;
    private VBox container;
    private Label gameSettingsTitle;
    private Button soundController;
    private double SCREENHEIGHT;
    private double SCREENWIDTH;
    public ImageView background;
    private MediaPlayer homeScreenMusic ;
    private final PropertyChangeSupport support;
    public Boolean exists = false;

    /**
     * Constructs the SettingsScene object with specified screen dimensions.
     *
     * @param screenHeight The height of the screen.
     * @param screenWidth  The width of the screen.
     */
    public SettingsScene( double screenHeight, double screenWidth){
        this.root = new Group();
        this.SCREENHEIGHT = screenHeight;
        this.SCREENWIDTH = screenWidth;
        initializeBackground();
        initializeSettingsScreen();
        this.scene = new Scene(root,screenWidth,screenHeight);
        this.support = new PropertyChangeSupport(this);
        this.homeScreenMusic = new MediaPlayer(new Media(getClass().getResource(DataUtilities.HomeMusic).toString()));
    }

    public void initializeSettingsScreen(){
        initializeButtons();
    }

    /**
     * Initializes the Settings page Menu
     */
    public void initializeButtons(){

        container = new VBox();
        container.setLayoutX(400);
        container.setLayoutY(100);

        this.homeButton = new Button("Home Button");
        homeButton.setLayoutX(448);
        homeButton.setLayoutY(470);
        homeButton.setMinHeight(75);
        homeButton.setMinWidth(210);
        homeButton.getStyleClass().add("score-label");

        this.homeButton.setOnMousePressed(e -> {
            homeScreenMusic.stop();
            goToScene(DataUtilities.HomeScene);
        });


        this.quitButton = new Button("Quit");
        quitButton.setLayoutX(663);
        quitButton.setLayoutY(470);
        quitButton.setMinHeight(75);
        quitButton.setMinWidth(190);
        quitButton.getStyleClass().add("score-label");

        this.quitButton.setOnMousePressed(e -> {
            System.exit(1);
        });

        this.soundController = new Button("Turn Music OFF");
        soundController.setLayoutX(663);
        soundController.setLayoutY(470);
        soundController.setMinHeight(75);
        soundController.setMinWidth(190);
        soundController.getStyleClass().add("score-label");
        this.quitButton.setOnMousePressed(e -> {
            DataUtilities.MUSICSTATUS = false;
            this.soundController.setText("Turn Music ON");
        });

        gameSettingsTitle = new Label("GAME SETTINGS");
        gameSettingsTitle.getStyleClass().add("score-label");

        container.getChildren().add(gameSettingsTitle);
        container.getChildren().add(soundController);
        container.getChildren().add(homeButton);
        container.getChildren().add(quitButton);
        root.getChildren().add(container);
        // Add a Label (Follows absolute reference). SIMILAR TO document.createElement().

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

}
