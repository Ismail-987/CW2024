package com.example.demo.scenes;



import java.util.*;
import java.util.stream.Collectors;
import java.beans.PropertyChangeSupport;


import com.example.demo.UIObjects.Containers.LoseScreen;
import com.example.demo.UIObjects.Containers.PauseScreen;
import com.example.demo.UIObjects.Images.actors.Projectile;
import com.example.demo.factories.LevelView;
import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.FighterPlane;
import com.example.demo.UIObjects.Images.actors.UserPlane;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;

public abstract class LevelParent {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 160;
	private static final int MILLISECOND_DELAY = 50;
	public final double screenHeight;
	public final double screenWidth;
	private final double enemyMaximumYPosition;
	public boolean exist = false;


	private ImageView pause_btn;
	private PauseScreen pauseScreen;

	private Label scoreLabel;
	private Label levelLabel;
	private int levelNo;
	private String levelName;


	private LoseScreen lossScreen;
	private Group pause_scene;
	private final Group root;

	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private ImageView background;
	private MediaPlayer backgroundMusic;


	private final PropertyChangeSupport support;

	private final List<ActiveActor> friendlyUnits;
	private final List<ActiveActor> enemyUnits;
	private final List<ActiveActor> userProjectiles;
	private final List<ActiveActor> enemyProjectiles;

	private int currentNumberOfEnemies;
	private LevelView levelView;

	public LevelParent(String backgroundImageName, String backgroundMusic, double screenHeight, double screenWidth, int playerInitialHealth,int levelNo, String levelName) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
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
		initializeTimeline();
		friendlyUnits.add(user);
	}


	public PropertyChangeSupport getSupport(){

		return this.support;
	}

	public void goToScene(String levelName) {
		winGame();
		exist = false;
		support.firePropertyChange("Page Change", null, levelName);
	}


	public void initializeBackground() {
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
		initializeBackground();
		initializeFriendlyUnits();
		initializePauseButton();
		levelView.initializeHeartDisplay();
		levelView.initializeShield();
		initializeLevelLabels();
		backgroundMusic.play();
		exist = true;
		return scene;
	}



	protected abstract void initializeFriendlyUnits();


	public void startGame() {
		background.requestFocus();
		timeline.play();
	}



	private void updateScene() {

		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}


	public void fireProjectile() {
		Projectile projectile = user.fireProjectile();
		projectile.getProjectileSound().play();
		root.getChildren().add(projectile);
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
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
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
		root.getChildren().add(initializeLooseScreen());
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

	public void createPauseScreen(){
		this.pauseScreen = new PauseScreen(375,100,this::resume_game,()->{goToScene(DataUtilities.HomeScene);},()->{FileUtility.saveGameStatus(levelName);});
	}

	public Group initializePauseScreen(){
		createPauseScreen();
		this.pause_scene = pauseScreen.get_scene_container();
		return pause_scene;
	}

	// --- These initializations have to have their own classes of factories.-------------------------------
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



	public Group initializeLooseScreen (){
		createLossScreen();
		return lossScreen.get_scene_container();
	}
	public void createLossScreen (){
		lossScreen = new LoseScreen(355,160);
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
