package com.example.demo.scenes;


import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.beans.PropertyChangeSupport;


import com.example.demo.UIObjects.Containers.WinScreen;
import com.example.demo.UIObjects.Images.actors.Projectile;
import com.example.demo.factories.LevelView;
import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.FighterPlane;
import com.example.demo.UIObjects.Images.actors.UserPlane;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group; // Container Node or Element. Inherits parent Class. This is like div tag.
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;

public abstract class LevelParent {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 160;
	private static final int MILLISECOND_DELAY = 50;
	public final double screenHeight;
	public final double screenWidth;
	private final double enemyMaximumYPosition;
	public boolean exist = false; // A FLAG to track the existence of a level.


	private ImageView pause_btn;

	private Button playButton;
	private Button saveButton;
	private Button quitButton;
	private Button informationButton;
	private Button homeButton;
	private Button settingsButton;


	private  Button lossHomeButton;
	private Button lostRestartButton;
	private  Button lostSkipLevelButton;
	private Button lostTipsButton;
	private Button  lostQuitButton;

	private Label scoreLabel;
	private Label levelLabel;
	private int levelNo;
	private String levelName;


	private Group lossScreen;
	private Group pause_scene;
	private final Group root;

	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;  // -- Scene is public for tracking.
	private ImageView background;
	private MediaPlayer backgroundMusic;


	private final PropertyChangeSupport support;  // Adds Observables

	private final List<ActiveActor> friendlyUnits;
	private final List<ActiveActor> enemyUnits;
	private final List<ActiveActor> userProjectiles;
	private final List<ActiveActor> enemyProjectiles;

	private int currentNumberOfEnemies;
	private LevelView levelView;  // Basically A Class API

	public LevelParent(String backgroundImageName, String backgroundMusic, double screenHeight, double screenWidth, int playerInitialHealth,int levelNo, String levelName) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight); // Attach Root node (tag) to the scene (HTML page)
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.support = new PropertyChangeSupport(this);


		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
		this.backgroundMusic = new MediaPlayer(new Media(getClass().getResource(backgroundMusic).toString()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelNo = levelNo;
		this.levelName = levelName;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline(); // Prepare the UI Loop and what will happen in it before setting the Scene.
		friendlyUnits.add(user);
	}


	public PropertyChangeSupport getSupport(){

		return this.support;
	}

	public void goToScene(String levelName) {
		winGame(); // Stop timeline for Level 1 (to avoid conflicts with timeline for level 2) and show win game pic.
		exist = false; // Flag to track existence.
		support.firePropertyChange("Page Change", null, levelName);  // Notify all observers with change of Level
	}


	public void initializeBackground() { // Made this method public
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setUserData("back_name");
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.SPACE) fireProjectile();
				if (kc == KeyCode.P) load_pause_screen();
			}
		});
		background.setOnKeyReleased(e -> {
			KeyCode kc = e.getCode();
			if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
		});
		root.getChildren().add(background);
	}

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground(); // Prepare the background in the scene before the UI LOOP begins.
		//levelView.addImagesToRoot();
		initializeFriendlyUnits(); // Prepare friendly units before the UI LOOP begins.
		initializePauseButton();
		levelView.initializeHeartDisplay();
		levelView.initializeShield();
		initializeLevelLabels();
		backgroundMusic.play();
		exist = true; // Added flag to track the existence of the Level Object.
		return scene;
	}



	protected abstract void initializeFriendlyUnits();


	public void startGame() {
		background.requestFocus();
		// Start a timeline or a gameloop.
		timeline.play();
	}


	//-- UI LOOP. For this case is the GAME LOOP.
	private void updateScene() {
		// Functions that will run on every cycle. Some may represent animation of various objects.
		// Some may be of Event handling i.e, they may use of user inputs.
		spawnEnemyUnits();
		updateActors(); // Update their positions. This will depend on Gamer Input i.e Event based.
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver(); // This also checks for nextLevel condition and notify observers.
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		// KeyFrame defines frames per second.
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}


	public void fireProjectile() {
		Projectile projectile = user.fireProjectile(); // Create new user projectile
		projectile.getProjectileSound().play();
		root.getChildren().add(projectile); // Add new projectile
		userProjectiles.add(projectile);
	}

// --------------------------------FUNCTIONS CALLED IN THE GAME LOOP (UI LOOP)------------------------------------------

// FUNCTION 1
	protected abstract void spawnEnemyUnits();

//  FUNCTION 2
	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}



// FUNCTION 3
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy ->
				spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(Projectile projectile) {
		if (projectile != null) {
			projectile.getProjectileSound().play();
			root.getChildren().add(projectile); // Add projectile to DOM.
			enemyProjectiles.add(projectile); // Add to the list of active enemy projectiles
		}
	}

// FUNCTION 4
	private void updateNumberOfEnemies() {

		currentNumberOfEnemies = enemyUnits.size();
	}

// FUNCTION 5
	private void handleEnemyPenetration() {
		for (ActiveActor enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				loseGame();
			}
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActor enemy) {

		return Math.abs(enemy.getTranslateX()) > screenWidth; // Get the translation from the starting position and check if is greater than screenWidth
	}

// FUNCTION 6
	private void handleUserProjectileCollisions() {

		handleCollisions(userProjectiles, enemyUnits);
}

// FUNCTION 7
	private void handleEnemyProjectileCollisions() {

		handleCollisions(enemyProjectiles, friendlyUnits);
}

// FUNCTION 8
	private void handlePlaneCollisions() {

		handleCollisions(friendlyUnits, enemyUnits);
}

// -- F(X) 6,7,8 DEPEND ON THIS
	private void handleCollisions(List<ActiveActor> actors1,
								  List<ActiveActor> actors2) {
		for (ActiveActor actor : actors2) {
			for (ActiveActor otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

// FUNCTION 9
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActor> actors) {
		List<ActiveActor> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

// FUNCTION 10
	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

// FUNCTION 11
	public void updateLevelView() {

		levelView.removeHearts(user.getHealth());
}

// FUNCTION 12
	protected abstract void checkIfGameOver();






	protected void winGame() {
		timeline.stop();
		backgroundMusic.stop();

	}

	protected void loseGame() {
		timeline.stop();
		backgroundMusic.stop();
		this.pause_btn.setVisible(false);
		initializeLooseScreen();
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActor enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

//------------------------------------------------ADDED CODES (FUNCTIONS)-----------------------------------------------

	// GETTERS AND SETTERS
	//------------------------------------------------------------------------------------------------------------------
	public  Timeline getTimeline (){
		return timeline;
	}

	public Scene getScene(){
		return this.scene;
	}

	public LevelView getLevelView(){
		return this.levelView;
	}

	public ImageView getPauseButton (){
		return pause_btn;
	}

	public MediaPlayer getBackgroundMusic(){
		return this.backgroundMusic;
	}

	public Label getScoreLabel(){
		return this.scoreLabel;
	}

	public Label getLevelLabel() {
		return levelLabel;
	}
	//OTHERS
	//------------------------------------------------------------------------------------------------------------------


	// PAUSE SCREEN SECTION.
	public void  initializePauseButton () {
		this.pause_btn = this.levelView.createPauseButton();
		this.pause_btn.setOnMousePressed(e -> {
			load_pause_screen();
		});
		root.getChildren().add(pause_btn);
	}
	public void load_pause_screen(){
		timeline.pause();
		backgroundMusic.pause();
		pause_btn.setVisible(false);
		root.getChildren().add(initializePauseScreen());
	}
	public void initializeLevelLabels (){
		scoreLabel = new Label("SCORE : ");
		scoreLabel.setLayoutX(870);
		scoreLabel.setLayoutY(30);
		scoreLabel.getStyleClass().add("score-label");

		levelLabel = new Label("LEVEL : ");
		if (levelNo == 4){
			levelLabel.setText("FINAL LEVEL "+" : "+levelName);
		}else {
			levelLabel.setText("LEVEL "+ levelNo +" : "+levelName);
		}

		levelLabel.setLayoutY(30);
		levelLabel.setLayoutX(468);
		levelLabel.getStyleClass().add("level-label");

		this.root.getChildren().add(scoreLabel);
		this.root.getChildren().add(levelLabel);
	}

	public Group initializePauseScreen (){
		this.pause_scene = levelView.createPauseScene(); // Bring screen background
		this.playButton = new Button();
		playButton.setMinWidth(147.6);
		playButton.setMinHeight(52.8);
		playButton.setLayoutX(47);
		playButton.setLayoutY(255.6);
		playButton.setStyle("-fx-background-color: transparent;");

		this.saveButton = new Button();
		saveButton.setMinWidth(147.6);
		saveButton.setMinHeight(52.8);
		saveButton.setLayoutX(224.2);
		saveButton.setLayoutY(255.6);
		saveButton.setStyle("-fx-background-color: transparent;");

		this.homeButton = new Button();
		homeButton.setMinWidth(147.6);
		homeButton.setMinHeight(52.8);
		homeButton.setLayoutX(224.2);
		homeButton.setLayoutY(392);
		homeButton.setStyle("-fx-background-color: transparent;");

		this.informationButton = new Button();
		informationButton.setMinWidth(147.6);
		informationButton.setMinHeight(52.8);
		informationButton.setLayoutX(47);
		informationButton.setLayoutY(392);
		informationButton.setStyle("-fx-background-color: transparent;");

		this.quitButton = new Button();
		quitButton.setMinWidth(147.6);
		quitButton.setMinHeight(52.8);
		quitButton.setLayoutX(404.2);
		quitButton.setLayoutY(392);
		quitButton.setStyle("-fx-background-color: transparent;");

		this.settingsButton = new Button();
		settingsButton.setMinWidth(147.6);
		settingsButton.setMinHeight(52.8);
		settingsButton.setLayoutX(404.2);
		settingsButton.setLayoutY(255.6);
		settingsButton.setStyle("-fx-background-color: transparent;");


		this.saveButton.setOnMousePressed(e -> {
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
					fileWriter.write("");
					System.out.println("Game level saved successfully!");
				}

			} catch (IOException ex) {
				ex.printStackTrace();
				throw new RuntimeException("Failed to save game status", ex);
			}
		});
		this.playButton.setOnMousePressed(e -> {
			resume_game();
		});
		this.quitButton.setOnMousePressed(e -> {
			System.exit(1);
		});
		this.informationButton.setOnMousePressed(e -> {
			resume_game();
		});
		this.settingsButton.setOnMousePressed(e -> {
			resume_game();
		});
		this.homeButton.setOnMousePressed(e -> {
			goToScene("com.example.demo.scenes.HomeScene");
		});

		pause_scene.getChildren().add(playButton);
		pause_scene.getChildren().add(saveButton);
		pause_scene.getChildren().add(homeButton);
		pause_scene.getChildren().add(informationButton);
		pause_scene.getChildren().add(settingsButton);
		pause_scene.getChildren().add(quitButton);
		return pause_scene;
	}

	public void initializeLooseScreen (){
		this.lossScreen = levelView.createLooseScreen();
		this.lostQuitButton = new Button("Quit");
		lostQuitButton.setMinWidth(179.6);
		lostQuitButton.setMinHeight(35.2);
		lostQuitButton.setLayoutX(383.6);
		lostQuitButton.setLayoutY(238);

		this.lossHomeButton = new Button("Home");
		lossHomeButton.setMinWidth(169.6);
		lossHomeButton.setMinHeight(35.2);
		lossHomeButton.setLayoutX(198.8);
		lossHomeButton.setLayoutY(378);

		this.lostRestartButton = new Button("Replay");
		lostRestartButton.setMinWidth(175.8);
		lostRestartButton.setMinHeight(35.2);
		lostRestartButton.setLayoutX(40);
		lostRestartButton.setLayoutY(240);

		this.lostTipsButton = new Button("Tips");
		lostTipsButton.setMinWidth(167.6);
		lostTipsButton.setMinHeight(35.2);
		lostTipsButton.setLayoutX(196.6);
		lostTipsButton.setLayoutY(425.2);

		this.lostSkipLevelButton = new Button("Skip level");
		lostSkipLevelButton.setMinWidth(169.6);
		lostSkipLevelButton.setMinHeight(35.2);
		lostSkipLevelButton.setLayoutX(214.2);
		lostSkipLevelButton.setLayoutY(238);

		lossScreen.getChildren().add(lostTipsButton);
		lossScreen.getChildren().add(lossHomeButton);
		lossScreen.getChildren().add(lostRestartButton);
		lossScreen.getChildren().add(lostSkipLevelButton);
		lossScreen.getChildren().add(lostQuitButton);

		this.root.getChildren().add(lossScreen);

	}

	public void resume_game(){
		pause_btn.setVisible(true);
		this.pause_scene.setVisible(false);
		timeline.play();
		backgroundMusic.play();
	}
	public void restart_game(){
		goToScene("com.example.demo.scenes.LevelOne");
		timeline.stop(); // Added
	}

}
