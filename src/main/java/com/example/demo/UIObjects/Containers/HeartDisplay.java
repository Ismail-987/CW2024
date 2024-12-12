package com.example.demo.UIObjects.Containers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;

/**
 * Displays a heart-based health bar using images, with functionality to add and remove hearts.
 */
public class HeartDisplay {

	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0;

	private HBox container;
	private double containerXPosition;
	private double containerYPosition;
	private int numberOfHeartsToDisplay;
	private Rectangle backgroundBar = new Rectangle(300, 30);

	// Foreground bar representing dynamic health size
	Rectangle foregroundBar = new Rectangle(300, 30);

	/**
	 * Constructs a HeartDisplay at specified x and y position with a given number of hearts.
	 *
	 * @param xPosition The x-coordinate for the health bar position.
	 * @param yPosition The y-coordinate for the health bar position.
	 * @param heartsToDisplay The number of hearts to display.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		backgroundBar.setFill(Color.DARKRED);
		foregroundBar.setFill(Color.LIMEGREEN);
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container for holding heart images.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);
	}

	/**
	 * Initializes hearts in the container.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));
			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	public void addOneHeart(){
		ImageView heart = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(HEART_IMAGE_NAME)).toExternalForm()));
		heart.setFitHeight(HEART_HEIGHT);
		heart.setPreserveRatio(true);
		container.getChildren().add(heart);
	}

	/**
	 * Removes one heart from the container.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
	}

	/**
	 * Returns the container with heart images.
	 *
	 * @return The HBox containing heart images.
	 */
	public HBox getContainer() {
		return container;
	}
}