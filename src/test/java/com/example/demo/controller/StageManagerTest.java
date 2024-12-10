package com.example.demo.controller;

import static org.mockito.Mockito.*;

import com.example.demo.controller.StageManager;
import com.example.demo.scenes.HomeScene;
import com.example.demo.scenes.LevelParent;
import com.example.demo.utilities.DataUtilities;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationTargetException;

class StageManagerTest extends ApplicationTest {

    private Stage mockStage;
    private StageManager stageManager;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        mockStage = mock(Stage.class);
        stageManager = new StageManager(mockStage);

        // Mock DataUtilities values
        DataUtilities.HomeScene = "com.example.demo.scenes.HomeScene";
    }

    @Test
    void testLaunchGame_DisplaysHomeScene() throws Exception {
        // Mock HomeScene behavior
        HomeScene mockHomeScene = mock(HomeScene.class);
        when(mockHomeScene.returnScene()).thenReturn(new Scene(null));
        when(mockHomeScene.exists).thenReturn(true);

        // Mock scene transition
        stageManager.launchGame();

        // Verify stage.show() and setScene() are called
        verify(mockStage, times(1)).show();
        verify(mockStage, times(1)).setScene(any(Scene.class));
    }

    @Test
    void testGoToScene_HomeScene_InitializesCorrectly() throws Exception {
        // Mock HomeScene behavior
        HomeScene mockHomeScene = mock(HomeScene.class);
        when(mockHomeScene.returnScene()).thenReturn(new Scene(null));

        // Simulate going to HomeScene
        stageManager.goToScene(DataUtilities.HomeScene);

        // Verify the stage is set with the scene
        verify(mockStage, times(1)).setScene(any(Scene.class));
    }

    @Test
    void testPropertyChange_TriggersSceneChange() throws Exception {
        // Mock event with new scene value
        String newScene = "com.example.demo.scenes.LevelOne";
        PropertyChangeEvent mockEvent = new PropertyChangeEvent(this, "scene", null, newScene);

        // Trigger propertyChange
        stageManager.propertyChange(mockEvent);

        // Verify the scene transition
        verify(mockStage, times(1)).setScene(any(Scene.class));
    }

    @Test
    void testGoToScene_InvalidScene_HandlesException() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // Set an invalid scene name
        String invalidScene = "com.example.nonexistent.Scene";

        // Call goToScene and expect it to handle exceptions
        stageManager.goToScene(invalidScene);

        // Verify no scene is set on the stage
        verify(mockStage, never()).setScene(any(Scene.class));
    }

    @Test
    void testGoToScene_CSSFileNotFound_ShowsError() throws Exception {
        // Mock LevelParent behavior with a missing CSS file
        LevelParent mockLevel = mock(LevelParent.class);
        when(mockLevel.initializeScene()).thenReturn(new Scene(null));
        when(mockLevel.getSupport()).thenReturn(null);

        // Inject mockLevel into StageManager via reflection if necessary
        stageManager.myLevel = mockLevel;

        // Trigger goToScene with a valid class name
        stageManager.goToScene("com.example.demo.scenes.LevelParent");

        // Verify no exceptions occur and scene is set
        verify(mockStage, times(1)).setScene(any(Scene.class));
    }
}
