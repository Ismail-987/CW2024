package com.example.demo.scenes;

import com.example.demo.UIObjects.Containers.WinScreen;
import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.EnemyPlane;
import com.example.demo.utilities.uiManagers.LevelView;
import com.example.demo.utilities.uiManagers.LevelViewLevelTwo;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;
import javafx.scene.Group;
import javafx.scene.media.MediaPlayer;


public class LevelTwo extends LevelParent{

    private WinScreen winScreen;
    private MediaPlayer youWinMusic = getGameState().gameWonMusic;
    private MediaPlayer youLostSound = getGameState().gameOverMusic;



    public LevelTwo() {

        super(DataUtilities.LevelTwoNumber);


    }

    @Override
    protected void checkIfGameOver() {
        if (getGameState().userIsDestroyed()) {
            loseGame();
            youLostSound.play();
        }
        if (userHasReachedKillTarget()) {
            getGameState().killTargetScenario();
            getLevelView().pauseButton.setVisible(false);
            getGameState().root.getChildren().add(initializeWinScreen());
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getGameState().getCurrentNumberOfEnemies();
        for (int i = 0; i < DataUtilities.LevelOneTotalEnemies - currentNumberOfEnemies; i++) {
            if (Math.random() < DataUtilities.LevelOneEnemySpawnProbability) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                ActiveActor newEnemy = new EnemyPlane(DataUtilities.ScreenWidth, newEnemyInitialYPosition);
                getGameState().addEnemyUnit(newEnemy);
            }
        }
    }

    @Override
    protected LevelView instantiateLevelView() {

        return new LevelViewLevelTwo(getRoot(), getGameState(),()->{goToScene(DataUtilities.HomeScene);});
    }

    private boolean userHasReachedKillTarget() {
        String score = ("SCORE : "+ getGameState().user.getNumberOfKills()+" /"+DataUtilities.LevelTwoNumberOfKills);
        getLevelView().scoreLabel.setText(score);
        return getGameState().user.getNumberOfKills() >= DataUtilities.LevelTwoNumberOfKills;
    }

    public Group initializeWinScreen(){
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
        return winScreen.get_scene_container();
    }


}
