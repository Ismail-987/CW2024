package com.example.demo.UIObjects.Images.actors;

import javafx.scene.media.MediaPlayer;

public class EnemyProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "enemyFire.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -10;
	private static final String PROJECTILE_NAME = "/com/example/demo/images/enemmySoundLevel1.mp3";

	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos,PROJECTILE_NAME);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

	public MediaPlayer getPrjectileSound(){
		return super.getProjectileSound();
	}
}
