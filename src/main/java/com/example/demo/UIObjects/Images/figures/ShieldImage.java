package com.example.demo.UIObjects.Images.figures;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Represents a shield image with specified size and position, which can be shown or hidden.
 */
public class ShieldImage extends ImageView {

	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	private static final int SHIELD_SIZE = 200;

	/**
	 * Constructs a ShieldImage with specified x and y position.
	 *
	 * @param xPosition The x-coordinate for the position of the shield.
	 * @param yPosition The y-coordinate for the position of the shield.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_NAME)).toExternalForm()));
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Makes the shield visible.
	 */
	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * Hides the shield from view.
	 */
	public void hideShield() {
		this.setVisible(false);
	}
}