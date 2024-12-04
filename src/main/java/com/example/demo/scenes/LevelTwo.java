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

/**
 * Represents the second level of the game, overseeing enemy spawning and win/loss conditions.
 */
public class LevelTwo extends LevelParent {

    private WinScreen winScreen;
    private MediaPlayer youWinMusic = getGameState().gameWonMusic;
    private MediaPlayer youLostSound = getGameState().gameOverMusic;

    /**
     * Constructs the LevelTwo object with initial settings.
     */
    public LevelTwo() {
        super(DataUtilities.LevelTwoNumber);
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
            getGameState().root.getChildren().add(initializeWinScreen());
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
     * Instantiates the LevelView specific to Level Two.
     *
     * @return a new LevelViewLevelTwo instance
     */
    @Override
    protected LevelView instantiateLevelView() {
        return new LevelViewLevelTwo(getRoot(), getGameState(), () -> { goToScene(DataUtilities.HomeScene); });
    }

    /**
     * Checks if the user has reached the required number of kills to win.
     *
     * @return true if kill target is reached, otherwise false
     */
    private boolean userHasReachedKillTarget() {
        String score = ("SCORE : " + getGameState().user.getNumberOfKills() + " /" + DataUtilities.LevelTwoNumberOfKills);
        getLevelView().scoreLabel.setText(score);
        return getGameState().user.getNumberOfKills() >= DataUtilities.LevelTwoNumberOfKills;
    }

    /**
     * Initializes and returns the win screen when the user wins the level.
     *
     * @return Group containing the win screen elements
     */
    public Group initializeWinScreen() {
        this.winScreen = new WinScreen(355, 175,
                () -> {
                    goToScene(DataUtilities.HomeScene);
                    youWinMusic.stop();
                },
                () -> {
                    goToScene(DataUtilities.LevelThree);
                    youWinMusic.stop();
                },
                () -> {
                    goToScene(DataUtilities.LevelTwo);
                    youWinMusic.stop();
                },
                () -> {
                    FileUtility.saveGameStatus(DataUtilities.LevelThree);
                });
        return winScreen.get_scene_container();
    }
}