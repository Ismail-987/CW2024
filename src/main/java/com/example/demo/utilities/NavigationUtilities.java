package com.example.demo.utilities;

import java.beans.PropertyChangeSupport;

public class NavigationUtilities {

    public NavigationUtilities(){

    }
    public static void goToSceen(PropertyChangeSupport support, String levelName){
        support.firePropertyChange("Page Change",null, levelName);
    }
}
