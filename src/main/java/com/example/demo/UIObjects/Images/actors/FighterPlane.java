package com.example.demo.UIObjects.Images.actors;


import com.example.demo.utilities.DataUtilities;

/**
 * Represents a fighter plane with the ability to take damage and fire projectiles.
 */
public abstract class FighterPlane extends ActiveActor {

	public int health;

	/**
	 * Constructs a FighterPlane with the specified parameters.
	 *
	 * @param imageName   The image file name for the plane.
	 * @param imageHeight The height of the plane's image.
	 * @param initialXPos The initial x-coordinate of the plane.
	 * @param initialYPos The initial y-coordinate of the plane.
	 * @param health      The initial health of the plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Fires a projectile from the fighter plane.
	 *
	 * @return a new Projectile object.
	 */
	public abstract Projectile fireProjectile();

	/**
	 * Reduces the health of the plane and destroys it if health reaches zero.
	 */
	@Override
	public void takeDamage() {
		if (DataUtilities.isPowerUpActive){
			this.health -= 2;
		}else {
			health--;
		}
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Calculates the X position for firing a projectile with given offset.
	 *
	 * @param xPositionOffset The offset for the X position.
	 * @return the calculated X position.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the Y position for firing a projectile with given offset.
	 *
	 * @param yPositionOffset The offset for the Y position.
	 * @return the calculated Y position.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the health of the plane is zero.
	 *
	 * @return true if health is zero, otherwise false.
	 */
	private boolean healthAtZero() {
		return health == 0 || health <= 0;
	}

	/**
	 * Retrieves the current health of the plane.
	 *
	 * @return the health value.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Updates the position of the fighter plane.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Checks if the fighter plane is shielded.
	 *
	 * @return true if shielded, otherwise false.
	 */
	public abstract Boolean isShielded();

	/**
	 * Updates the state of the fighter plane.
	 */
	public abstract void updateActor();

	/**
	 * Retrieves the initial health of the plane.
	 *
	 * @return the initial health value.
	 */
	public abstract int getInitHealth();
}