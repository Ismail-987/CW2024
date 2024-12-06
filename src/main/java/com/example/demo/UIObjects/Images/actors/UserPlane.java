package com.example.demo.UIObjects.Images.actors;

/**
 * Represents the user's plane, capable of moving vertically and firing projectiles.
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane1.png";
	private static final double Y_UPPER_BOUND = 40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 55;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int velocityMultiplier;
	private int numberOfKills;

	/**
	 * Constructs a UserPlane with specified initial health.
	 *
	 * @param initialHealth The initial health of the user plane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
	}

	/**
	 * Updates the vertical position of the user plane.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
	}

	/**
	 * Updates the state of the user plane.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * Fires a projectile from the user plane.
	 *
	 * @return a new UserProjectile object.
	 */
	@Override
	public Projectile fireProjectile() {
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * Checks if the user plane is moving.
	 *
	 * @return true if moving, otherwise false.
	 */
	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	/**
	 * Moves the plane upwards by setting velocity.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Moves the plane downwards by setting velocity.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Stops the movement of the plane.
	 */
	public void stop() {
		velocityMultiplier = 0;
	}

	/**
	 * Retrieves the number of kills achieved by the user plane.
	 *
	 * @return the number of kills.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count for the user plane.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Increases the kill count of the user plane by 2.
	 * This method is intended to be used when a power-up is active,
	 * allowing the user plane to more effectively eliminate enemies.
	 */
	public void powerUpKillCount() {
		numberOfKills += 2;
	}

	/**
	 * Retrieves the initial health of the user plane.
	 *
	 * @return the initial health value.
	 */
	@Override
	public int getInitHealth() {
		return 0;
	}

	/**
	 * Indicates that the user plane is not shielded.
	 *
	 * @return false since the user plane does not have a shield.
	 */
	@Override
	public Boolean isShielded() {
		return false;
	}
}