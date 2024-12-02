package com.example.demo.scenes;


import com.example.demo.UIObjects.Containers.WinScreen;
import com.example.demo.factories.LevelView;
import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.EnemyPlane;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LevelOne extends LevelParent {

	private WinScreen winScreen;
	private MediaPlayer youWinMusic = new MediaPlayer(new Media(getClass().getResource(DataUtilities.YouWinMusic).toString()));
	private MediaPlayer youLostSound = new MediaPlayer(new Media(getClass().getResource(DataUtilities.YouLoseMusic).toString()));


	public LevelOne(double screenHeight, double screenWidth) {

		super(DataUtilities.LevelOneBackgroundImage,DataUtilities.LevelOneMusic, screenHeight, screenWidth, DataUtilities.LevelOnePlayerHealth,DataUtilities.LevelOneNumber,DataUtilities.LevelOneName);

	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
			youLostSound.play();
		}
		if (userHasReachedKillTarget()) {
			getTimeline().stop();
			getBackgroundMusic().stop();
			getPauseButton().setVisible(false);
			getRoot().getChildren().add(initializeWinScreen());
			youWinMusic.play();
		}
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser()); // Add user object
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < DataUtilities.LevelOneTotalEnemies - currentNumberOfEnemies; i++) {
			if (Math.random() < DataUtilities.LevelOneEnemySpawnProbability) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActor newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	@Override
	protected LevelView instantiateLevelView() {

		return new LevelView(getRoot(), DataUtilities.LevelOnePlayerHealth, DataUtilities.LevelOneBackgroundImage, getUser());
	}

	private boolean userHasReachedKillTarget() {
		String score = ("SCORE : "+ getUser().getNumberOfKills()+" /"+DataUtilities.LevelOneNumberOfKills);
		getScoreLabel().setText(score);
		return getUser().getNumberOfKills() >= DataUtilities.LevelOneNumberOfKills;
	}


	public Group initializeWinScreen() {
		createWinScreen();
		return winScreen.get_scene_container();
	}
	public void createWinScreen(){
		this.winScreen = new WinScreen(355,175,
				()->{
			goToScene(DataUtilities.HomeScene);
			System.out.println("You reach");
			youWinMusic.stop();


				},
				()->{
			goToScene(DataUtilities.LevelTwo);
			youWinMusic.stop();
				},
				()->{
			goToScene(DataUtilities.LevelOne);
			youWinMusic.stop();
				},
				()->{
					FileUtility.saveGameStatus(DataUtilities.LevelTwo);

				});
	}




}
