package com.example.demo.UIObjects.Images.actors;

import com.example.demo.Destructible;
import javafx.scene.image.*;
import java.util.Objects;

/**
 * Abstract representation of an active actor in the game, providing methods for movement and destruction.
 */
public abstract class ActiveActor extends ImageView implements Destructible {

	private static final String IMAGE_LOCATION = "/com/example/demo/images/";
	private boolean isDestroyed;

	/**
	 * Constructs an ActiveActor with specified parameters.
	 *
	 * @param imageName   The name of the image file for the actor.
	 * @param imageHeight The height of the actor's image.
	 * @param initialXPos The initial x-coordinate position.
	 * @param initialYPos The initial y-coordinate position.
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(Objects.requireNonNull(getClass().getResource(IMAGE_LOCATION + imageName)).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
		this.isDestroyed = false;
	}

	/**
	 * Updates the position of the actor. Must be implemented by subclasses.
	 */
	public abstract void updatePosition();

	/**
	 * Moves the actor horizontally by the specified amount.
	 *
	 * @param horizontalMove The amount to move horizontally.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * Moves the actor vertically by the specified amount.
	 *
	 * @param verticalMove The amount to move vertically.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

	/**
	 * Applies damage to the actor. Must be implemented by subclasses.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Destroys the actor, marking it as destroyed.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destroyed state of the actor.
	 *
	 * @param isDestroyed True if the actor is destroyed, otherwise false.
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks if the actor is destroyed.
	 *
	 * @return True if the actor is destroyed, otherwise false.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}

	/**
	 * Updates the actor's state. Must be implemented by subclasses.
	 */
	public abstract void updateActor();
}