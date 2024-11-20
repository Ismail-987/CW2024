package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;
import java.beans.PropertyChangeSupport;


import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.FighterPlane;
import com.example.demo.UIObjects.Images.actors.UserPlane;
import com.example.demo.UIObjects.Images.figures.PlayButton;
import com.example.demo.UIObjects.Images.figures.RestartButton;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group; // Container Node or Element. Inherits parent Class. This is like div tag.
import javafx.scene.Scene;
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
	private PlayButton play_btn;
	private RestartButton restart_btn;
	private Group pause_scene;
	//private final Button quit_Button;
	private final Group root;
	public final Timeline timeline;
	public final UserPlane user;
	public final Scene scene;  // -- Scene is public for tracking.
	public ImageView background;
	private final PropertyChangeSupport support;  // Adds Observables

	private final List<ActiveActor> friendlyUnits;
	private final List<ActiveActor> enemyUnits;
	private final List<ActiveActor> userProjectiles;
	private final List<ActiveActor> enemyProjectiles;

	private int currentNumberOfEnemies;
	private LevelView levelView;  // Basically A Class API

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight); // Attach Root node (tag) to the scene (HTML page)
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();
		this.support = new PropertyChangeSupport(this);

//		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.background = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(backgroundImageName)).toExternalForm()));
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

	public void goToNextLevel(String levelName) {
		winGame(); // Stop timeline for Level 1 (to avoid conflicts with timeline for level 2) and show win game pic.
		exist = false; // Flag to track existence.
		support.firePropertyChange("Page Change", null, levelName);  // Notify all observers with change of Level
	}


	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground(); // Prepare the background in the scene before the UI LOOP begins.
		initializeFriendlyUnits(); // Prepare friendly units before the UI LOOP begins.
		initializePauseButton();
		levelView.initializeHeartDisplay();
		exist = true; // Added flag to track the existence of the Level Object.
		return scene;
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
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActor enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
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
		levelView.showWinImage();
	}

	protected void loseGame() {
		timeline.stop();
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
		pause_btn.setVisible(false);
		root.getChildren().add(init_pause_scene());
	}
	public Group init_pause_scene (){
		this.pause_scene = levelView.createPauseScene();
		play_btn = new PlayButton(50,150);
		restart_btn = new RestartButton(400,150);
		this.restart_btn.setOnMousePressed(e -> {
			restart_game();
		});
		this.play_btn.setOnMousePressed(e -> {
			resume_game();
		});
		pause_scene.getChildren().add(play_btn);
		pause_scene.getChildren().add(restart_btn);
		return pause_scene;
	}
	public void resume_game(){
		pause_btn.setVisible(true);
		this.pause_scene.setVisible(false);
		timeline.play();
	}
	public void restart_game(){
		goToNextLevel("com.example.demo.LevelOne");
		timeline.stop();
	}
}
