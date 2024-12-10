package com.example.demo.controller;

import static org.mockito.Mockito.*;

import com.example.demo.controller.Main;
import com.example.demo.controller.StageManager;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.stage.Stage;

public class MainTest extends ApplicationTest {

    private StageManager mockStageManager;
    private Main main;

    @Override
    public void start(Stage stage) throws Exception {
        mockStageManager = mock(StageManager.class);
        main = new Main(mockStageManager); // Inject the mock
        main.start(stage);
    }

    @Test
    void testStart() throws Exception {
        // Verify that launchGame() is called on the mock
        verify(mockStageManager, times(1)).launchGame();
    }
}
