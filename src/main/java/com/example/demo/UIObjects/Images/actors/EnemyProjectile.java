package com.example.demo.UIObjects.Images.actors;

import javafx.scene.media.MediaPlayer;

/**
 * Represents a projectile fired by enemy planes, moving with specific velocity.
 */
public class EnemyProjectile extends Projectile {

	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -10;
	private static final String PROJECTILE_NAME = "/com/example/demo/images/enemmySoundLevel1.mp3";

	/**
	 * Constructs an EnemyProjectile with specified initial position.
	 *
	 * @param initialXPos The initial x-coordinate for the projectile.
	 * @param initialYPos The initial y-coordinate for the projectile.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, PROJECTILE_NAME);
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
	public MediaPlayer getPrjectileSound() {
		return super.getProjectileSound();
	}
}