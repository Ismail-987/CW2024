package com.example.demo.UIObjects.Containers;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents the game finished screen with a background image and specified layout position.
 */
public class GameFinishedScreen {

    private Group root;
    private String image = "/com/example/demo/images/gameFinished.jpg";
    private ImageView pauseBackground = new ImageView();
    private Timeline timeline; // Placeholder for future use

    /**
     * Constructs a GameFinishedScreen at specified x and y position.
     *
     * @param Xposition The x-coordinate for the layout position.
     * @param Yposition The y-coordinate for the layout position.
     */
    public GameFinishedScreen(double Xposition, double Yposition) {
        this.root = new Group();
        this.root.setLayoutX(Xposition);
        this.root.setLayoutY(Yposition);
        this.pauseBackground.setImage(new Image(Objects.requireNonNull(getClass().getResource(image)).toExternalForm()));
        this.pauseBackground.setFitWidth(600);
        this.pauseBackground.setFitHeight(513);
        root.getChildren().add(pauseBackground);
    }

    /**
     * Returns the root container for the game finished screen.
     *
     * @return The group containing the screen elements.
     */
    public Group get_scene_container() {
        return root;
    }
}