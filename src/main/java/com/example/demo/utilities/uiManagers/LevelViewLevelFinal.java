package com.example.demo.utilities.uiManagers;

import com.example.demo.UIObjects.Containers.GameFinishedScreen;
import com.example.demo.UIObjects.Images.figures.ShieldImage;
import com.example.demo.utilities.GameState;
import javafx.scene.Group;

public class LevelViewLevelFinal extends LevelView {

	private static final int SHIELD_X_POSITION = 880;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;

	
	public LevelViewLevelFinal(Group root, GameState gamestate,Runnable goToHomeScreen) {
		super(root,gamestate,goToHomeScreen);
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
