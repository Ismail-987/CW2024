package com.example.demo.scenes;

import com.example.demo.UIObjects.Containers.WinScreen;
import com.example.demo.UIObjects.Images.actors.ActiveActor;
import com.example.demo.UIObjects.Images.actors.EnemyPlane;
import com.example.demo.utilities.uiManagers.LevelView;
import com.example.demo.utilities.uiManagers.LevelViewLevelThree;
import com.example.demo.utilities.DataUtilities;
import javafx.scene.media.MediaPlayer;

/**
 * Represents the third level of the game, handling enemy spawning and win/loss conditions.
 */
public class LevelThree extends LevelParent {

    private WinScreen winScreen;
    private MediaPlayer youWinMusic = getGameState().gameWonMusic;
    private MediaPlayer youLostSound = getGameState().gameOverMusic;

    /**
     * Constructs the LevelThree object with initial settings.
     */
    public LevelThree() {
        super(DataUtilities.LevelThreeNumber);
    }

    /**
     * Checks if the game is over based on user destruction or kill target achievement.
     */
    @Override
    protected void checkIfGameOver() {
        if (getGameState().userIsDestroyed()) {
            loseGame();
            youLostSound.play();
        }
        if (userHasReachedKillTarget()) {
            getGameState().killTargetScenario();
            getLevelView().pauseButton.setVisible(false);
            getLevelView().initializeWinScreen();
        }
    }

    /**
     * Spawns enemy units on the scene with a defined probability.
     */
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

    /**
     * Instantiates the LevelView specific to Level Three.
     *
     * @return a new LevelViewLevelThree instance
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelThree(getRoot(), getGameState(), () -> { goToScene(DataUtilities.HomeScene); });
    }

    /**
     * This method updates the levelView which in turns updates the User Interface of the game.
     */
    @Override
    public void updateLevelView(){
        getLevelView().removeHearts(getGameState().user.getHealth());
    }

    /**
     * Checks if the user has reached the required number of kills to win.
     *
     * @return true if kill target is reached, otherwise false
     */
    private boolean userHasReachedKillTarget() {
        String score = "";
        if (getGameState().user.getNumberOfKills()> DataUtilities.LevelThreeNumberOfKills){
            score = ("SCORE : " + DataUtilities.LevelThreeNumberOfKills + " /" + DataUtilities.LevelThreeNumberOfKills);
        }else {
            score = ("SCORE : " + getGameState().user.getNumberOfKills() + " /" + DataUtilities.LevelThreeNumberOfKills);
        }
        getLevelView().scoreLabel.setText(score);
        return getGameState().user.getNumberOfKills() >= DataUtilities.LevelThreeNumberOfKills;
    }

}