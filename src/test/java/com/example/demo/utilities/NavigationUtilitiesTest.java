package com.example.demo.utilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for NavigationUtilities.
 */
public class NavigationUtilitiesTest {

    private PropertyChangeSupport mockSupport;

    @BeforeEach
    void setUp() {
        // Mock PropertyChangeSupport for testing
        mockSupport = Mockito.spy(new PropertyChangeSupport(this));
    }

    @Test
    void testGoToScene_TriggersPropertyChange() {
        // Given
        String levelName = "com.example.demo.scenes.HomeScene";

        // When
        NavigationUtilities.goToScene(mockSupport, levelName);

        // Then
        // Verify that firePropertyChange was called with expected arguments
        verify(mockSupport, times(1)).firePropertyChange("Page Change", null, levelName);
    }

    @Test
    void testGoToScene_withEmptyLevelName() {
        // Given
        String levelName = "";

        // When
        NavigationUtilities.goToScene(mockSupport, levelName);

        // Then
        verify(mockSupport, times(1)).firePropertyChange("Page Change", null, "");
    }

    @Test
    void testGoToScene_withNullLevelName() {
        // Given
        String levelName = null;

        // When
        NavigationUtilities.goToScene(mockSupport, levelName);

        // Then
        verify(mockSupport, times(1)).firePropertyChange("Page Change", null, null);
    }
}
