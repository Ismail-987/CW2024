package com.example.demo.scenes;


import com.example.demo.utilities.uiManagers.LevelView;
import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.EnemyPlane;
import com.example.demo.utilities.uiManagers.LevelViewLevelOne;
import com.example.demo.utilities.DataUtilities;

/**
 * Represents the first level of the game, managing enemy spawning, game progression, and win/loss conditions.
 */
public class LevelOne extends LevelParent {


	/**
	 * Constructs the LevelOne object with initial settings.
	 */
	public LevelOne() {
		super(DataUtilities.LevelOneNumber);
	}

	/**
	 * Checks whether the game is over based on the player's condition or kill target achievement.
	 */
	@Override
	protected void checkIfGameOver() {
		if (getGameState().userIsDestroyed()) {
			loseGame();
		}
		if (userHasReachedKillTarget()) {
			getGameState().killTargetScenario();
			getLevelView().pauseButton.setVisible(false);
			getLevelView().initializeWinScreen();
		}
	}

	/**
	 * Spawns enemy units on the scene with a defined probability.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getGameState().getCurrentNumberOfEnemies();
		for (int i = 0; i < DataUtilities.LevelOneTotalEnemies - currentNumberOfEnemies; i++) {
			if (Math.random() < DataUtilities.LevelOneEnemySpawnProbability) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActor newEnemy = new EnemyPlane(DataUtilities.ScreenWidth, newEnemyInitialYPosition);
				getGameState().addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * Instantiates the LevelView specific to Level One.
	 *
	 * @return a new LevelViewLevelOne instance
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelViewLevelOne(getRoot(), getGameState(), () -> { goToScene(DataUtilities.HomeScene); });
	}

	/**
	 * Checks if the user has reached the required number of kills.
	 *
	 * @return true if kill target is reached, otherwise false
	 */
	private boolean userHasReachedKillTarget() {
		String score = ("SCORE : " + getGameState().user.getNumberOfKills() + " /" + DataUtilities.LevelOneNumberOfKills);
		getLevelView().scoreLabel.setText(score);
		return getGameState().user.getNumberOfKills() >= DataUtilities.LevelOneNumberOfKills;
	}

}