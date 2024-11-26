package com.example.demo.scenes;

import com.example.demo.factories.LevelView;
import com.example.demo.factories.LevelViewLevelTwo;
import com.example.demo.UIObjects.Images.actors.Boss;
import javafx.scene.Group;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/homescreenbackground.jpg";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;
	private Group gameFinished;

	public LevelTwo(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		this.boss = new Boss();

	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		else if (boss.isDestroyed()) {
			winGame();
			this.getPauseButton().setVisible(false);
			getRoot().getChildren().add(getLevelView().createGameFinishedScreen());
		}
	}

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH,BACKGROUND_IMAGE_NAME,getUser()); // Polymorphism
		// This above function returns a subclass object to a parent class variable.
	}

}
