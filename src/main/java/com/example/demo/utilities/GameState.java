package com.example.demo.utilities;

import com.example.demo.UIObjects.Images.actors.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

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
    public int levelNumber;
    public int initialHealth;
    public String levelBackground;
    public String levelMusic;
    public Group root;


    public GameState (Runnable updateScene,Runnable looseGame, Group root, int levelNumber){
        this.levelNumber = levelNumber;
        setInitialPlayerHealth();
        setLevelName();
        setLevelClassName();
        setLevelBackground();
        setLevelMusic();

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

    public void initializeTimeline() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame gameLoop = new KeyFrame(Duration.millis(DataUtilities.MILISECOND_DELAY), e -> updateScene.run());
        timeline.getKeyFrames().add(gameLoop);
    }

    public void runGameLoopThread(){
        updateActors();
        generateEnemyFire();
        updateNumberOfEnemies();
        handleEnemyPenetration();
        CollisionManager.collisionManager(friendlyUnits,enemyUnits,userProjectiles,enemyProjectiles,root);
        updateKillCount();
    }

    public void updateActors() {
        friendlyUnits.forEach(plane -> plane.updateActor());
        enemyUnits.forEach(enemy -> enemy.updateActor());
        userProjectiles.forEach(projectile -> projectile.updateActor());
        enemyProjectiles.forEach(projectile -> projectile.updateActor());
    }

    public void generateEnemyFire() {
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
    public void updateNumberOfEnemies() {

        currentNumberOfEnemies = enemyUnits.size();
    }

    public void updateKillCount() {
        for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
            user.incrementKillCount();
        }
    }
    public int getCurrentNumberOfEnemies() {
        return enemyUnits.size();
    }
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
    public void addEnemyUnit(ActiveActor enemy) {
        enemyUnits.add(enemy);
        root.getChildren().add(enemy);
    }

    public boolean userIsDestroyed() {
        return user.isDestroyed();
    }
    public void initializeFriendlyUnits() {
        root.getChildren().add(user);
    }
    public void killTargetScenario(){
        timeline.stop();
        Backgroundmusic.stop();
        gameWonMusic.play();
    }

    public void sceneInitializationScenario(){
        initializeFriendlyUnits();
        Backgroundmusic.play();
    }

    private void setLevelClassName() {
        if(levelNumber == 1){
            levelClassName = DataUtilities.LevelOne;
        }else if(levelNumber == 2){
            levelClassName = DataUtilities.LevelTwo;
        }
        else if(levelNumber == 3){
            levelClassName = DataUtilities.LevelThree;
        }
        else{
            levelClassName = DataUtilities.LevelFinal;
        }
    }
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
