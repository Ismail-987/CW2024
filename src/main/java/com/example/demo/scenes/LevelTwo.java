package com.example.demo.scenes;

import com.example.demo.UIObjects.Containers.WinScreen;
import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.EnemyPlane;
import com.example.demo.factories.LevelView;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;
import javafx.scene.Group;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class LevelTwo extends LevelParent{

    private WinScreen winScreen;
    private MediaPlayer youWinMusic = new MediaPlayer(new Media(getClass().getResource(DataUtilities.YouWinMusic).toString()));
    private MediaPlayer youLostSound = new MediaPlayer(new Media(getClass().getResource(DataUtilities.YouLoseMusic).toString()));



    public LevelTwo(double screenHeight, double screenWidth) {

        super(DataUtilities.LevelTwoBackgroundImage,DataUtilities.LevelTwoMusic, screenHeight, screenWidth, DataUtilities.LevelTwoPlayerHealth,DataUtilities.LevelTwoNumber,DataUtilities.LevelTwoName);


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
            getRoot().getChildren().add( initializeWinScreen());
            youWinMusic.play();
        }
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < DataUtilities.LevelTwoTotalEnemies - currentNumberOfEnemies; i++) {
            if (Math.random() < DataUtilities.LevelTwoEnemySpawnProbability) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActor newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
                addEnemyUnit(newEnemy);
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {

        return new LevelView(getRoot(),DataUtilities.LevelTwoPlayerHealth, DataUtilities.LevelTwoBackgroundImage, getUser());
    }

    private boolean userHasReachedKillTarget() {
        String score = ("SCORE : "+ getUser().getNumberOfKills()+" /"+DataUtilities.LevelTwoNumberOfKills);
        getScoreLabel().setText(score);
        return getUser().getNumberOfKills() >= DataUtilities.LevelTwoNumberOfKills;
    }

    // Put this in winScreen Factory
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
                    goToScene(DataUtilities.LevelThree);
                    youWinMusic.stop();
                },
                ()->{
                    goToScene(DataUtilities.LevelTwo);
                    youWinMusic.stop();
                },
                ()->{
                    FileUtility.saveGameStatus(DataUtilities.LevelThree);
                });
    }

}
