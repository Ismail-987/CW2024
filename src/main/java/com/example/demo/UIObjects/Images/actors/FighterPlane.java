package com.example.demo.UIObjects.Images.actors;

public abstract class FighterPlane extends ActiveActor {

	private int health;

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	public abstract Projectile fireProjectile();
	
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy(); // Sets the isdestroyed flag to true.
		}
	}

	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	private boolean healthAtZero() {

		return health == 0;
	}

	public int getHealth() {

		return health;
	}

	@Override
	public abstract void updatePosition();

	public abstract Boolean isShielded();

	public abstract void updateActor();

	public abstract int getInitHealth();
		
}
