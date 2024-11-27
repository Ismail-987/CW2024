package com.example.demo.scenes;

import com.example.demo.factories.LevelView;
import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.EnemyPlane;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private  static  final String BACKGROUNDMUSIC ="/com/example/demo/images/level1music.mp3" ;
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
	private MediaPlayer youWinMusic = new MediaPlayer(new Media(getClass().getResource("/com/example/demo/images/youwinmusic.mp3").toString()));
	private MediaPlayer youLostSound = new MediaPlayer(new Media(getClass().getResource("/com/example/demo/images/youLostSound.mp3").toString()));



	public LevelOne(double screenHeight, double screenWidth) {

		super(BACKGROUND_IMAGE_NAME,BACKGROUNDMUSIC, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);


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
		winQuitButton.setMinWidth(125.6);
		winQuitButton.setMinHeight(74.8);
		winQuitButton.setLayoutX(87.2);
		winQuitButton.setLayoutY(350.8);

		this.winNextLevelButton = new Button("Next Level");
		winNextLevelButton.setMinWidth(103.6);
		winNextLevelButton.setMinHeight(74.8);
		winNextLevelButton.setLayoutX(67.2);
		winNextLevelButton.setLayoutY(262.2);

		this.winReplayLevelButton = new Button("Replay level");
		winReplayLevelButton.setMinWidth(125.6);
		winReplayLevelButton.setMinHeight(74.8);
		winReplayLevelButton.setLayoutX(386.8);
		winReplayLevelButton.setLayoutY(345.8);

		this.winHomeButton = new Button("Home Button");
		winHomeButton.setMinWidth(103.6);
		winHomeButton.setMinHeight(74.8);
		winHomeButton.setLayoutX(431.2);
		winHomeButton.setLayoutY(262.2);

		this.winSaveButton = new Button("Save");
		winSaveButton.setMinWidth(103.6);
		winSaveButton.setMinHeight(74.8);
		winSaveButton.setLayoutX(185.2);
		winSaveButton.setLayoutY(262.2);

		this.winSettingsButton = new Button("Settings");
		winSettingsButton.setMinWidth(103.6);
		winSettingsButton.setMinHeight(74.8);
		winSettingsButton.setLayoutX(307);
		winSettingsButton.setLayoutY(262.2);


		this.winSettingsButton.setOnMousePressed(e -> {
			System.exit(1);
		});

		this.winSaveButton.setOnMousePressed(e -> {
			System.exit(1);
		});

		this.winQuitButton.setOnMousePressed(e -> {
			System.exit(1);
		});

		this.winNextLevelButton.setOnMousePressed(e -> {
			youWinMusic.stop();
			goToScene(NEXT_LEVEL);

		});

		this.winHomeButton.setOnMousePressed(e -> {
			youWinMusic.stop();
			goToScene("com.example.demo.scenes.HomeScene");
		});

		winScreen.getChildren().add(winSaveButton);
		winScreen.getChildren().add(winSettingsButton);
		winScreen.getChildren().add(winQuitButton);
		winScreen.getChildren().add(winNextLevelButton);
		winScreen.getChildren().add(winReplayLevelButton);
		winScreen.getChildren().add(winHomeButton);

		return winScreen;
	}

}
