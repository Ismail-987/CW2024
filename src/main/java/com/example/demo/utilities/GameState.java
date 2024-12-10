package com.example.demo.utilities;

import com.example.demo.UIObjects.Images.actors.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the state of the game, including unit interactions, music, and timeline management.
 */
public class GameState {
    public  Timeline timeline;
    public static Runnable updateScene;
    public static Runnable LooseGame;
    public final List<ActiveActor> friendlyUnits;
    public final List<ActiveActor> enemyUnits;
    public final List<ActiveActor> userProjectiles;
    public final List<ActiveActor> enemyProjectiles;
    public MediaPlayer Backgroundmusic;
    public MediaPlayer gameOverMusic;
    public MediaPlayer gameWonMusic;

    public int currentNumberOfEnemies;
    public int maximumEnemyPosition;
    public final UserPlane user;
    public final Boss boss;
    public String levelName;
    public String levelClassName;
    public String NextLevelClassName;
    public int levelNumber;
    public int initialHealth;
    public String levelBackground;
    public String levelMusic;
    public Group root;
    public Boolean exist = false;
    public PropertyChangeSupport support;


    /**
     * Constructs a new GameState for a specified level.
     *
     * @param updateScene the Runnable to update the scene
     * @param looseGame the Runnable to handle game loss
     * @param root the root group node
     * @param levelNumber the level number
     */
    public GameState (Runnable updateScene,Runnable looseGame, Group root, int levelNumber, PropertyChangeSupport support){
        this.levelNumber = levelNumber;
        setInitialPlayerHealth();
        setLevelName();
        setLevelClassName();
        setLevelBackground();
        setLevelMusic();

        this.support = support;
        this.friendlyUnits = new ArrayList<>();
        this.enemyUnits = new ArrayList<>();
        this.userProjectiles = new ArrayList<>();
        this.enemyProjectiles = new ArrayList<>();
        this.user = new UserPlane(initialHealth);
        this.boss = new Boss();
        timeline = new Timeline();
        this.root = root;
        this.currentNumberOfEnemies = 0;
        this.maximumEnemyPosition = DataUtilities.ScreenHeight - DataUtilities.SCREENHEIGHTADJUSTMENT;
        this.updateScene = updateScene;
        this.LooseGame = looseGame;
        this.Backgroundmusic = new MediaPlayer( new Media(getClass().getResource(levelMusic).toString()));
        this.gameWonMusic = new MediaPlayer(new Media(getClass().getResource(DataUtilities.YouWinMusic).toString()));
        this.gameOverMusic = new MediaPlayer(new Media(getClass().getResource(DataUtilities.YouLoseMusic).toString()));
    }

    /**
     * Initializes the game timeline for animations.
     */
    public void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(DataUtilities.MILISECOND_DELAY), e -> updateScene.run());
        timeline.getKeyFrames().add(gameLoop);
    }

    /**
     * Runs the game loop thread.
     */
    public void runGameLoopThread(){
        updateActors();
        generateEnemyFire();
        updateNumberOfEnemies();
        handleEnemyPenetration();
        CollisionManager.collisionManager(friendlyUnits,enemyUnits,userProjectiles,enemyProjectiles,root);
        updateKillCount();
    }

    /**
     * Updates the state of all active actors.
     */
    public void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

    /**
     * Generates and fires enemy projectiles.
     */
    public void generateEnemyFire() {
        enemyUnits.forEach(enemy ->
                spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
    }

    /**
     * Updates the current number of enemies in the game.
     */
    private void spawnEnemyProjectile(Projectile projectile) {
        if (projectile != null) {
            projectile.getProjectileSound().play();
            root.getChildren().add(projectile);
            enemyProjectiles.add(projectile);
        }
    }

    /**
     * Updates the current number of enemies in the game.
     */
    public void updateNumberOfEnemies() {

        currentNumberOfEnemies = enemyUnits.size();
    }

    /**
     * Updates the kill count for the user based on the difference between the initial
     * and current number of enemies. If a power-up is active, it increments the user's
     * kill count by a higher value; otherwise, it increments it normally.
     */
    public void updateKillCount() {
        for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
            if(DataUtilities.isPowerUpActive){
                user.powerUpKillCount();
            }else {
                user.incrementKillCount();
            }
        }
    }

    /**
     * Returns the current number of enemies.
     *
     * @return the number of enemy units
     */
    public int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }

    /**
     * Handles the penalty when an enemy penetrates defenses.
     */
    public void handleEnemyPenetration() {
        for (ActiveActor enemy : enemyUnits) {
            if (enemyHasPenetratedDefenses(enemy)) {
                LooseGame.run();
            }
        }
    }

    private boolean enemyHasPenetratedDefenses(ActiveActor enemy) {
        return Math.abs(enemy.getTranslateX()) > DataUtilities.ScreenWidth;
    }

    /**
     * Adds a new enemy unit to the game.
     *
     * @param enemy the enemy to add
     */
    public void addEnemyUnit(ActiveActor enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    /**
     * Checks if the user has been destroyed.
     *
     * @return true if the user is destroyed, false otherwise
     */
    public boolean userIsDestroyed() {
        return user.isDestroyed();
    }

    /**
     * Initializes friendly units for the game.
     */
    public void initializeFriendlyUnits() {
        root.getChildren().add(user);
    }

    /**
     * Handles the scenario when a target is killed.
     */
    public void killTargetScenario(){
        timeline.stop();
        Backgroundmusic.stop();
        gameWonMusic.play();
    }

    /**
     * Initializes the scene for this game state.
     */
    public void sceneInitializationScenario(){
        initializeFriendlyUnits();
        exist = true;
        Backgroundmusic.play();
    }

    /**
     * Handles the scenario when the game is lost by stopping ongoing background music,
     * playing the game over music, and stopping the game timeline.
     */
    public void loseGameScenario(){
        Backgroundmusic.stop();
        gameOverMusic.play();
        timeline.stop();
    }

    /**
     * Sets the class name for the level
     * Also sets the class name for the level after this.
     */
    private void setLevelClassName() {
        if(levelNumber == 1){
            levelClassName = DataUtilities.LevelOne;
            NextLevelClassName = DataUtilities.LevelTwo;
        }else if(levelNumber == 2){
            levelClassName = DataUtilities.LevelTwo;
            NextLevelClassName = DataUtilities.LevelThree;
        }
        else if(levelNumber == 3){
            levelClassName = DataUtilities.LevelThree;
            NextLevelClassName = DataUtilities.LevelFinal;
        }
        else{
            levelClassName = DataUtilities.LevelFinal;
            NextLevelClassName = "";
        }
    }

    /**
     * Sets the background for the level.
     */
    private void setLevelBackground() {
        if(levelNumber == 1){
            levelBackground = DataUtilities.LevelOneBackgroundImage;
        }
        else if(levelNumber == 2){
            levelBackground = DataUtilities.LevelTwoBackgroundImage;
        }
        else if(levelNumber == 3){
            levelBackground = DataUtilities.LevelThreeBackgroundImage;
        }
        else{
            levelBackground = DataUtilities.LevelFinalBackgroundImage;
        }
    }

    /**
     * Sets the background music for the level.
     */
    private void setLevelMusic() {
        if(levelNumber == 1){
            levelMusic = DataUtilities.LevelOneMusic;
        }
        else if(levelNumber == 2){
            levelMusic = DataUtilities.LevelTwoMusic;
        }
        else if(levelNumber == 3){
            levelMusic = DataUtilities.LevelThreeMusic;
        }
        else{
            levelMusic = DataUtilities.LevelFinalMusic;
        }
    }

    /**
     * Sets the name for the level.
     */
    private void setLevelName() {
        if(levelNumber == 1){
            levelName = DataUtilities.LevelOneName;
        }
        else if(levelNumber == 2){
            levelName = DataUtilities.LevelTwoName;
        }
        else if(levelNumber == 3){
            levelName = DataUtilities.LevelThreeName;
        }
        else{
            levelName = DataUtilities.LevelFinalName;
        }
    }

    /**
     * Sets the initial health for the player.
     */
    private void setInitialPlayerHealth(){
        if(levelNumber == 1){
            initialHealth = DataUtilities.LevelOnePlayerHealth;
        }
        else if(levelNumber == 2){
            initialHealth = DataUtilities.LevelTwoPlayerHealth;
        }
        else if(levelNumber == 3){
            initialHealth = DataUtilities.LevelThreePlayerHealth;
        }
        else{
            initialHealth = DataUtilities.LevelFinalPlayerHealth;
        }
    }

}
