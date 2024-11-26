package com.example.demo.scenes;

//import java.applet.AudioClip;
import java.util.*;
import java.util.stream.Collectors;
import java.beans.PropertyChangeSupport;


import com.example.demo.UIObjects.Containers.WinScreen;
import com.example.demo.factories.LevelView;
import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.FighterPlane;
import com.example.demo.UIObjects.Images.actors.UserPlane;
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

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	public final double screenHeight;
	public final double screenWidth;
	private final double enemyMaximumYPosition;
	public boolean exist = false; // A FLAG to track the existence of a level.


	private ImageView pause_btn;
	private  Button playButton;
	private Button restartButton;
	private  Button quitButton;
	private Button informationButton;
	private Button  homeButton;
	private Button settingsButton;


	private  Button lossHomeButton;
	private Button lostRestartButton;
	private  Button lostSkipLevelButton;
	private Button lostTipsButton;
	private Button  lostQuitButton;


	private Group lossScreen;
	private Group pause_scene;
	//private final Button quit_Button;
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

	public LevelParent(String backgroundImageName,String backgroundMusic, double screenHeight, double screenWidth, int playerInitialHealth) {
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
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline(); // Prepare the UI Loop and what will happen in it before setting the Scene.
		friendlyUnits.add(user);
	}


//	public void goToNextLevel(String levelName) {
//		winGame(); // Stop timeline for Level 1 (to avoid conflicts with timeline for level 2) and show win game pic.
//		exist = false; // Flag to track existence.
//		setChanged();
//		notifyObservers(levelName); // Notify all observers with change of Level
//	}

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
		levelView.addImagesToRoot();
		levelView.showShield();
		initializeFriendlyUnits(); // Prepare friendly units before the UI LOOP begins.
		initializePauseButton();
		levelView.initializeHeartDisplay();
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
		ActiveActor projectile = user.fireProjectile(); // Create new user projectile
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
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActor projectile) {
		if (projectile != null) {
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
	private void updateLevelView() {

		levelView.removeHearts(user.getHealth());
}

// FUNCTION 12
	protected abstract void checkIfGameOver();






	protected void winGame() {
		timeline.stop();
		backgroundMusic.stop();
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
		backgroundMusic.stop();
		this.pause_btn.setVisible(false);
		levelView.showGameOverImage();
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
	public Group initializePauseScreen (){
		this.pause_scene = levelView.createPauseScene(); // Bring screen background
		this.playButton = new Button();
		playButton.setMinWidth(147.6);
		playButton.setMinHeight(52.8);
		playButton.setLayoutX(47);
		playButton.setLayoutY(255.6);
		playButton.setStyle("-fx-background-color: transparent;");

		this.restartButton = new Button();
		restartButton.setMinWidth(147.6);
		restartButton.setMinHeight(52.8);
		restartButton.setLayoutX(224.2);
		restartButton.setLayoutY(255.6);
		restartButton.setStyle("-fx-background-color: transparent;");

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


		this.restartButton.setOnMousePressed(e -> {
			restart_game();
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
		pause_scene.getChildren().add(restartButton);
		pause_scene.getChildren().add(homeButton);
		pause_scene.getChildren().add(informationButton);
		pause_scene.getChildren().add(settingsButton);
		pause_scene.getChildren().add(quitButton);
		return pause_scene;
	}

	public void intializeLooseScreen (){
		this.lossScreen = levelView.createLooseScreen();
		this.lostQuitButton = new Button("Quit");
		lostQuitButton.setMinWidth(147.6);
		lostQuitButton.setMinHeight(52.8);
		lostQuitButton.setLayoutX(47);
		lostQuitButton.setLayoutY(255.6);

		this.playButton = new Button();
		playButton.setMinWidth(147.6);
		playButton.setMinHeight(52.8);
		playButton.setLayoutX(47);
		playButton.setLayoutY(255.6);

		this.playButton = new Button();
		playButton.setMinWidth(147.6);
		playButton.setMinHeight(52.8);
		playButton.setLayoutX(47);
		playButton.setLayoutY(255.6);

		this.playButton = new Button();
		playButton.setMinWidth(147.6);
		playButton.setMinHeight(52.8);
		playButton.setLayoutX(47);
		playButton.setLayoutY(255.6);

		this.playButton = new Button();
		playButton.setMinWidth(147.6);
		playButton.setMinHeight(52.8);
		playButton.setLayoutX(47);
		playButton.setLayoutY(255.6);



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


	public void goToHomeScene(String sceneName){
		timeline.stop();
		support.firePropertyChange("Page Change", null, sceneName);

	}
}
