package com.example.demo.UIObjects.Images.actors;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Represents a projectile with sound, capable of being destroyed upon impact.
 */
public abstract class Projectile extends ActiveActor {

	private MediaPlayer projectileSound;

	/**
	 * Constructs a Projectile with specified parameters.
	 *
	 * @param imageName      The image file name for the projectile.
	 * @param imageHeight    The height of the projectile's image.
	 * @param initialXPos    The initial x-coordinate of the projectile.
	 * @param initialYPos    The initial y-coordinate of the projectile.
	 * @param projectileName The name of the sound file for the projectile.
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, String projectileName) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.projectileSound = new MediaPlayer(new Media(getClass().getResource(projectileName).toString()));
	}

	/**
	 * Destroys the projectile when it takes damage.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * Updates the position of the projectile.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the state of the projectile.
	 */
	public abstract void updateActor();

	/**
	 * Retrieves the sound associated with the projectile.
	 *
	 * @return the MediaPlayer object for the projectile's sound.
	 */
	public MediaPlayer getProjectileSound() {
		return this.projectileSound;
	}
}