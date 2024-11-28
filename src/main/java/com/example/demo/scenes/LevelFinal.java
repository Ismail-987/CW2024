package com.example.demo.scenes;

import com.example.demo.UIObjects.Images.actors.FighterPlane;
import com.example.demo.factories.LevelView;
import com.example.demo.factories.LevelViewLevelFinal;
import com.example.demo.factories.LevelViewLevelFinal;
import com.example.demo.UIObjects.Images.actors.Boss;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LevelFinal extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/homescreenbackground.jpg";
	private static final String BACKGROUNDMUSIC =  "/com/example/demo/images/level1music.mp3";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private static final int CURRENT_LEVEL_NUMBER = 4;
	private static final String CURRENT_LEVEL_NAME = "FINAL BOSS";
	private static final String NEXT_LEVEL = "com.example.demo.scenes.LevelOne";
	private final FighterPlane boss;
	private LevelViewLevelFinal levelView;
	private Group gameFinished;
	private MediaPlayer youWinMusic = new MediaPlayer(new Media(getClass().getResource("/com/example/demo/images/youwinmusic.mp3").toString()));

	public LevelFinal(double screenHeight, double screenWidth) {
		super(BACKGROUND_IMAGE_NAME,BACKGROUNDMUSIC, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH,CURRENT_LEVEL_NUMBER,CURRENT_LEVEL_NAME);
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
			youWinMusic.play();
			this.getPauseButton().setVisible(false);
			getRoot().getChildren().add(getLevelView().createGameFinishedScreen());
			try {
				// Define the path to the file in a platform-independent way
				Path savedStatusPath = Paths.get("src", "main", "resources", "gameStatus", "gameStatus.txt");

				// Print resolved absolute path
				System.out.println("Resolved Path: " + savedStatusPath.toAbsolutePath());

				// Ensure parent directories exist
				Files.createDirectories(savedStatusPath.getParent());
				System.out.println("Parent directories ensured.");

				if(!Files.exists(savedStatusPath)){
					// Attempt to create the file unconditionally
					Files.createFile(savedStatusPath); // Force file creation
					System.out.println("File created successfully at: " + savedStatusPath.toAbsolutePath());
				}

				// Write to the file
				try (FileWriter fileWriter = new FileWriter(savedStatusPath.toFile(), false)) {
					fileWriter.write(NEXT_LEVEL);
					System.out.println("Game level saved successfully!");
				}

			} catch (IOException ex) {
				ex.printStackTrace();
				throw new RuntimeException("Failed to save game status", ex);
			}

		}else{
			getScoreLabel().setText("SCORE : "+(boss.getInitHealth()- boss.getHealth())+ "/ "+ boss.getInitHealth());
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
		levelView = new LevelViewLevelFinal(getRoot(), PLAYER_INITIAL_HEALTH,BACKGROUND_IMAGE_NAME,getUser());
		return levelView; // Polymorphism
		// This above function returns a subclass object to a parent class variable.
	}

	@Override
	public void updateLevelView(){
		levelView.removeHearts(getUser().getHealth());
		if (boss.isShielded()){
			levelView.showShield();
			levelView.getShield().setLayoutY(boss.getLayoutY()+boss.getTranslateY());
		}else{
			levelView.hideShield();
		}

	}
}
