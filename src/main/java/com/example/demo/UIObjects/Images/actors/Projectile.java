package com.example.demo.UIObjects.Images.actors;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public abstract class Projectile extends ActiveActor {
	private MediaPlayer projectileSound;

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos, String projectileName) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.projectileSound= new MediaPlayer(new Media(getClass().getResource(projectileName).toString()));
	}



	@Override
	public void takeDamage() {
		this.destroy();
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateActor();

	public MediaPlayer getProjectileSound(){
		return this.projectileSound;
	};



}
