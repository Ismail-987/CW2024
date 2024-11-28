package com.example.demo.factories;

import com.example.demo.UIObjects.Containers.GameFinishedScreen;
import com.example.demo.UIObjects.Images.actors.UserPlane;
import com.example.demo.UIObjects.Images.figures.ShieldImage;
import javafx.scene.Group;

public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 880;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;
	private Group winScreen;
	
	public LevelViewLevelTwo(Group root, int heartsToDisplay, String backgorundImageName, UserPlane user) {
		super(root, heartsToDisplay, backgorundImageName, user);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);


	}

	public void showShield() {

		shieldImage.setVisible(true);
	}

	public void hideShield() {

		shieldImage.setVisible(false);
	}


	public Group createGameFinishedScreen (){
		return new GameFinishedScreen(375,100).get_scene_container();
	}

	@Override
	public void initializeShield (){
		root.getChildren().add(shieldImage);
	}

	@Override
	public ShieldImage getShield() {
		return shieldImage;
	}
}
