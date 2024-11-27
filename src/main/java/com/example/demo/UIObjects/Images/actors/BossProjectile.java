package com.example.demo.UIObjects.Images.actors;

import javafx.scene.media.MediaPlayer;

public class BossProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 75;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;
	private static final String PROJECTILE_NAME = "/com/example/demo/images/enemmySoundLevel1.mp3";


	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos,PROJECTILE_NAME);
	}

	@Override
	public void updatePosition() {

		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	
	@Override
	public void updateActor() {

		updatePosition();
	}

	@Override
	public MediaPlayer getProjectileSound() {
		return super.getProjectileSound();
	}
}
