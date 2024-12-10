package com.example.demo.utilities.uiManagers;

import com.example.demo.UIObjects.Containers.LoseScreen;
import com.example.demo.UIObjects.Containers.PauseScreen;
import com.example.demo.UIObjects.Containers.WinScreen;
import com.example.demo.UIObjects.Images.figures.ShieldImage;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;
import com.example.demo.utilities.GameState;
import javafx.scene.Group;
import javafx.scene.control.Button;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for LevelView class.
 */
public class LevelViewTest {

    private Group mockRoot;
    private GameState mockGameState;
    private LevelView levelView;
    private PauseScreen mockPauseScreen;
    private WinScreen mockWinScreen;
    private LoseScreen mockLoseScreen;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockRoot = mock(Group.class);
        mockGameState = mock(GameState.class);
        mockPauseScreen = mock(PauseScreen.class);
        mockWinScreen = mock(WinScreen.class);
        mockLoseScreen = mock(LoseScreen.class);

        // Create the LevelView instance with mocks
        levelView = new LevelView(mockRoot, mockGameState, () -> System.out.println("Go to home scene"));
        levelView.setPauseScreen(mockPauseScreen);
        levelView.setWinScreen(mockWinScreen);
        levelView.setLossScreen(mockLoseScreen);
    }

    @Test
    void testLevelViewInitializer() {
        // Call the initializer
        levelView.levelViewInitializer();

        // Verify that the necessary components are initialized
        verify(mockRoot, times(1)).getChildren();
    }

    @Test
    void testPauseButtonLogic() {
        // Simulate clicking pause
        levelView.initializePauseButton();

        // Simulate a button click
        levelView.pauseButton.setOnMousePressed(e -> {
            levelView.load_pause_screen();
        });

        // Simulate loading the pause screen
        levelView.load_pause_screen();

        verify(mockGameState, times(1)).timeline.pause();
        verify(mockGameState, times(1)).Backgroundmusic.pause();
        verify(mockPauseScreen, times(1)).get_scene_container();
    }

    @Test
    void testPowerUpButtonLogic() {
        // Simulate the initialization of the power-up button
        levelView.initializePowerUpButton();

        // Simulate clicking the button
        levelView.powerUpButton.fire();

        assertFalse(levelView.powerUpButton.isVisible(), "The power-up button should hide after being clicked");
        assertTrue(DataUtilities.isPowerUpActive, "PowerUp should activate after clicking the button");
    }

    @Test
    void testGameWinLogic() {
        // Simulate the win screen initialization
        levelView.initializeWinScreen();

        // Verify the interaction with `mockWinScreen`
        verify(mockRoot, times(1)).getChildren();
        verify(mockWinScreen, times(1)).get_scene_container();
    }

    @Test
    void testGameLoseLogic() {
        // Simulate the lose screen initialization
        levelView.looseGameScenario();

        verify(mockGameState, times(1)).loseGameScenario();
        verify(mockLoseScreen, times(1)).get_scene_container();
        verify(mockRoot, times(1)).getChildren();
    }

    @Test
    void testPauseScreenBehavior() {
        levelView.initializePauseScreen();

        verify(mockRoot, times(1)).getChildren();
        verify(mockPauseScreen, times(1)).get_scene_container();
    }
}
