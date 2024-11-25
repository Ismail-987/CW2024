package com.example.demo.scenes;

import com.example.demo.factories.LevelView;
import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.EnemyPlane;
import javafx.scene.Group;
import javafx.scene.control.Button;

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.scenes.LevelTwo";
	private static final int TOTAL_ENEMIES = 3;
	private static final int KILLS_TO_ADVANCE = 2;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;

	private  Button winNextLevelButton;
	private Button winReplayLevelButton;
	private  Button winQuitButton;
	private Button winSaveButton;
	private Button  winHomeButton;
	private Button winSettingsButton;
	private Group winScreen;


	public LevelOne(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);

	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame();
		}
		if (userHasReachedKillTarget()) {
			getTimeline().stop();
			getPauseButton().setVisible(false);
			getRoot().getChildren().add(initializeWinScreen());
//			goToScene(NEXT_LEVEL); // Inform Observer To change page / Screen to level 2 page or screen
		}
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser()); // Add user object
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActor newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	@Override
	protected LevelView instantiateLevelView() {

		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH, BACKGROUND_IMAGE_NAME, getUser());
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}


	public Group initializeWinScreen(){
		this.winScreen = getLevelView().createWinScreen();

		this.winQuitButton = new Button("Quit Game");
		winQuitButton.setMinWidth(147.6);
		winQuitButton.setMinHeight(52.8);
		winQuitButton.setLayoutX(10.2);
		winQuitButton.setLayoutY(392);

		this.winNextLevelButton = new Button("Next Level");
		winNextLevelButton.setMinWidth(147.6);
		winNextLevelButton.setMinHeight(52.8);
		winNextLevelButton.setLayoutX(100.2);
		winNextLevelButton.setLayoutY(392);

		this.winReplayLevelButton = new Button("Replay level");
		winReplayLevelButton.setMinWidth(147.6);
		winReplayLevelButton.setMinHeight(52.8);
		winReplayLevelButton.setLayoutX(224.2);
		winReplayLevelButton.setLayoutY(202);

		this.winHomeButton = new Button("Home Button");
		winHomeButton.setMinWidth(147.6);
		winHomeButton.setMinHeight(52.8);
		winHomeButton.setLayoutX(224.2);
		winHomeButton.setLayoutY(392);

		this.winQuitButton.setOnMousePressed(e -> {
			System.exit(1);
		});

		this.winNextLevelButton.setOnMousePressed(e -> {
			goToScene(NEXT_LEVEL);
		});

		this.winHomeButton.setOnMousePressed(e -> {
			goToScene("com.example.demo.scenes.HomeScene");
		});

		winScreen.getChildren().add(winQuitButton);
		winScreen.getChildren().add(winNextLevelButton);
		winScreen.getChildren().add(winReplayLevelButton);
		winScreen.getChildren().add(winHomeButton);

		return winScreen;
	}

}
