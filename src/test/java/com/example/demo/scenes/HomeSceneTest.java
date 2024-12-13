package com.example.demo.scenes;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.demo.scenes.HomeScene;
import com.example.demo.utilities.DataUtilities;
import com.example.demo.utilities.FileUtility;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class HomeSceneTest extends ApplicationTest {

    private HomeScene homeScene;

    @BeforeEach
    void setUp() {
        // Mock DataUtilities values
        DataUtilities.HomeMusic = "/com/example/demo/images/homemenuviewmusic.mp3";
        DataUtilities.HomeMenuBackgroundImage = "/com/example/demo/images/homescreen.jpg";
        DataUtilities.HomeBackgroundImage = "/com/example/demo/images/homescreenbackground2.jpg";

        homeScene = new HomeScene(800, 600);
    }

    @Test
    void testSceneInitialization() {
        // Validate scene dimensions
        Scene scene = homeScene.returnScene();
        assertNotNull(scene, "Scene should be initialized");
        assertEquals(800, scene.getHeight(), "Scene height should match");
        assertEquals(600, scene.getWidth(), "Scene width should match");

        // Validate root group
        Group root = homeScene.getRoot();
        assertNotNull(root, "Root group should be initialized");
        assertEquals(7, root.getChildren().size(), "Root group should contain all UI elements");
    }

    @Test
    void testButtonsAreInitialized() {
        // Validate each button
        Button sgButton = homeScene.getSgbutton();
        assertNotNull(sgButton, "Start game button should be initialized");
        assertEquals(448, sgButton.getLayoutX(), "Start game button X position should match");
        assertEquals(320, sgButton.getLayoutY(), "Start game button Y position should match");

        Button quitButton = homeScene.getQuitButton();
        assertNotNull(quitButton, "Quit button should be initialized");
        assertEquals(743, quitButton.getLayoutX(), "Quit button X position should match");
        assertEquals(320, quitButton.getLayoutY(), "Quit button Y position should match");
    }

    @Test
    void testButtonActions() {
        // Mock FileUtility for continue button behavior
        FileUtility mockFileUtility = mock(FileUtility.class);
        when(mockFileUtility.readGameStatus()).thenReturn("com.example.demo.scenes.LevelOne");

        // Simulate button presses
        homeScene.getQuitButton().fire();
        assertEquals(false, homeScene.getHomeScreenMusic().onPlayingProperty(), "Music should stop when quit button is pressed");

        homeScene.getContinueButton().fire();
        assertEquals(false, homeScene.getHomeScreenMusic().onPlayingProperty(), "Music should stop when continue button is pressed");
    }

    @Test
    void testMusicInitializationAndPlayback() {
        MediaPlayer mediaPlayer = homeScene.getHomeScreenMusic();
        assertNotNull(mediaPlayer, "MediaPlayer should be initialized");
        assertDoesNotThrow(() -> mediaPlayer.play(), "MediaPlayer should play without throwing exceptions");
        assertDoesNotThrow(() -> mediaPlayer.stop(), "MediaPlayer should stop without throwing exceptions");
    }

    @Test
    void testLoadLevelFiresPropertyChange() {
        // Add a property change listener to capture events
        homeScene.getSupport().addPropertyChangeListener(evt -> {
            assertEquals("Page Changes", evt.getPropertyName(), "Property name should match");
            assertEquals("com.example.demo.scenes.LevelOne", evt.getNewValue(), "New value should be the level name");
        });

        // Trigger load_level
        homeScene.goToScene("com.example.demo.scenes.LevelOne");
    }
}
