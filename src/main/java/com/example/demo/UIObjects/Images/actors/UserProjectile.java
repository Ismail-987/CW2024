package com.example.demo.UIObjects.Images.actors;

import javafx.scene.media.MediaPlayer;

/**
 * Represents a projectile fired by the user, moving with a specified velocity.
 */
public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 125;
	private static final int HORIZONTAL_VELOCITY = 15;
	private static final String PROJECTILE_SOUND = "/com/example/demo/images/playerSound.mp3";

	/**
	 * Constructs a UserProjectile with specified initial position.
	 *
	 * @param initialXPos The initial x-coordinate of the projectile.
	 * @param initialYPos The initial y-coordinate of the projectile.
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, PROJECTILE_SOUND);
	}

	/**
	 * Updates the horizontal position of the projectile.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the projectile, such as its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Retrieves the sound associated with the projectile.
	 *
	 * @return the MediaPlayer object for the projectile's sound.
	 */
	public MediaPlayer getProjectileSound() {
		return super.getProjectileSound();
	}
}