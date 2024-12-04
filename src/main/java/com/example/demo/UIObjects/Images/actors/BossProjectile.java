package com.example.demo.UIObjects.Images.actors;

import javafx.scene.media.MediaPlayer;

/**
 * Represents a projectile fired by the boss, with specific velocity and sound effect.
 */
public class BossProjectile extends Projectile {

	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 75;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;
	private static final String PROJECTILE_NAME = "/com/example/demo/images/enemmySoundLevel1.mp3";

	/**
	 * Constructs a BossProjectile with a specified initial Y position.
	 *
	 * @param initialYPos The initial y-coordinate for the projectile.
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos, PROJECTILE_NAME);
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
	@Override
	public MediaPlayer getProjectileSound() {
		return super.getProjectileSound();
	}
}