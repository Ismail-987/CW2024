package com.example.demo.utilities;

import java.beans.PropertyChangeSupport;

/**
 * A utility class providing navigation functionalities for scene transitions in an application.
 */
public class NavigationUtilities {

    public NavigationUtilities(){

    }

    /**
     * Triggers a property change event to transition the application to a different scene.
     *
     * @param support   The PropertyChangeSupport instance used to notify listeners about the scene change.
     * @param levelName The name of the scene or level to switch to.
     */
    public static void goToScene(PropertyChangeSupport support, String levelName){
        support.firePropertyChange("Page Change",null, levelName);
    }
}
