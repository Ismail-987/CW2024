package com.example.demo.UIObjects.Images.actors;

public abstract class Projectile extends ActiveActor {

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	@Override
	public void takeDamage() {
		this.destroy();
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateActor();

}
