package com.example.demo.scenes;

import com.example.demo.UIObjects.Containers.WinScreen;
import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.EnemyPlane;
import com.example.demo.factories.LevelView;
import com.example.demo.factories.LevelViewLevelThree;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LevelThree extends LevelParent{

    private WinScreen winScreen;
    private MediaPlayer youWinMusic = new MediaPlayer(new Media(getClass().getResource(DataUtilities.YouWinMusic).toString()));
    private MediaPlayer youLostSound = new MediaPlayer(new Media(getClass().getResource(DataUtilities.YouLoseMusic).toString()));


    public LevelThree(double screenHeight, double screenWidth) {
        super(DataUtilities.LevelThreeBackgroundImage,DataUtilities.LevelThreeMusic, screenHeight, screenWidth, DataUtilities.LevelOnePlayerHealth,DataUtilities.LevelThreeNumber,DataUtilities.LevelThreeName);
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
        for (int i = 0; i < DataUtilities.LevelThreeTotalEnemies - currentNumberOfEnemies; i++) {
            if (Math.random() < DataUtilities.LevelThreeEnemySpawnProbability) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActor newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {

        return new LevelViewLevelThree(getRoot(), DataUtilities.LevelThreePlayerHealth, DataUtilities.LevelThreeBackgroundImage, getUser());
    }

    private boolean userHasReachedKillTarget() {
        String score = ("SCORE : "+ getUser().getNumberOfKills()+" /"+DataUtilities.LevelThreeNumberOfKills);
        getScoreLabel().setText(score);
        return getUser().getNumberOfKills() >= DataUtilities.LevelThreeNumberOfKills;
    }


    // TO WINSCREEN.JAVA
    public Group initializeWinScreen(){
        createWinScreen();
        return winScreen.get_scene_container();

    }
    public void createWinScreen(){
        this.winScreen = new WinScreen(355,175,
                ()->{
                    goToScene(DataUtilities.HomeScene);
                    youWinMusic.stop();
                },
                ()->{
                    goToScene(DataUtilities.LevelFinal);
                    youWinMusic.stop();
                },
                ()->{
                    goToScene(DataUtilities.LevelThree);
                    youWinMusic.stop();
                },
                ()->{
                    FileUtility.saveGameStatus(DataUtilities.LevelFinal);
                });
    }

}
