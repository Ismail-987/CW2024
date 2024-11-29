package com.example.demo.UIObjects.Images.actors;

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

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
	}
	
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
	
	@Override
	public void updateActor() {

		updatePosition();
	}
	
	@Override
	public Projectile fireProjectile() {
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMoving() {

		return velocityMultiplier != 0;
	}

	public void moveUp() {

		velocityMultiplier = -1; // velocityMultiplier is a flag to indicate movement and of what type (direction of the velocity).
	}

	public void moveDown() {

		velocityMultiplier = 1;
	}

	public void stop() {

		velocityMultiplier = 0;
	}

	public int getNumberOfKills() {

		return numberOfKills;
	}

	public void incrementKillCount() {

		numberOfKills++;
	}

	@Override
	public int getInitHealth(){
		return 0;
	}
	@Override
	public Boolean isShielded (){
		return false;
	}

}
