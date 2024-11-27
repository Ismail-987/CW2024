package com.example.demo.UIObjects.Images.actors;

import javafx.scene.media.MediaPlayer;

public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 125;
	private static final int HORIZONTAL_VELOCITY = 15;
	private static final String PROJECTILE_SOUND = "/com/example/demo/images/playerSound.mp3";

	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos,PROJECTILE_SOUND);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}
	
	@Override
	public void updateActor() {
		updatePosition();
	}


	public MediaPlayer getProjectileSound(){
		return super.getProjectileSound();
	}
	
}
