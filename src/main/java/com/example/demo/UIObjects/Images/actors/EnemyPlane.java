package com.example.demo.UIObjects.Images.actors;

/**
 * Represents an enemy plane with basic movement and firing functionality.
 */
public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 150;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	/**
	 * Constructs an EnemyPlane with the specified initial position.
	 *
	 * @param initialXPos The initial x-coordinate for the enemy plane.
	 * @param initialYPos The initial y-coordinate for the enemy plane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the enemy plane, moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile with a certain probability.
	 *
	 * @return a new EnemyProjectile if firing occurs, otherwise null.
	 */
	@Override
	public Projectile fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the state of the enemy plane, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Indicates that the enemy plane is not shielded.
	 *
	 * @return false since enemy planes do not have shields.
	 */
	@Override
	public Boolean isShielded() {
		return false;
	}

	/**
	 * Retrieves the initial health of the enemy plane.
	 *
	 * @return the initial health value.
	 */
	@Override
	public int getInitHealth() {
		return 0;
	}
}