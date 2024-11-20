package com.example.demo.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import com.example.demo.scenes.LevelOne;
import com.example.demo.scenes.HomeScene;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.scenes.LevelParent;

public class StageManager implements PropertyChangeListener {

	private static final String HOME_SCENE = "com.example.demo.scenes.HomeScene";
	private final Stage stage;
	private  LevelParent myLevel = new LevelOne(10,20); // A copy of a level for tracking.

	// Constructor
	public StageManager(Stage stage) {

		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToLevel(HOME_SCENE);

	}
			// This is Link Element. THE MANAGER OF THE STAGE
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			// Create Class Object that represent ClassName Class
			if(Objects.equals(className, HOME_SCENE)){
				Class<?> myClass = Class.forName(className);
				Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
				HomeScene homeScene = (HomeScene) constructor.newInstance(stage.getHeight(), stage.getWidth());
//				homeScreen.addObserver(this);
				homeScene.getSupport().addPropertyChangeListener(this);// Add Listeners Or Observers.
				Scene scene = homeScene.init_home_scene(); // Web page for level 1.
				stage.setScene(scene); // THIS IS THE <a> TAG TO CHANGE PAGES.
 			}
			else {

				if (myLevel.exist) {
//					myLevel.timeline.play();  // Resume
//					myLevel.background.requestFocus(); // timeline.pause() removes focus.
//					stage.setScene(myLevel.scene);
				} else {
					Class<?> myClass = Class.forName(className);
					Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
					myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth()); // Create Level 1 Screen or Page
//					myLevel.addObserver(this);
					myLevel.getSupport().addPropertyChangeListener(this); // Add a SUBSCRIBER.
					Scene scene = myLevel.initializeScene(); // Web page for level 1.
					stage.setScene(scene); // THIS IS THE <a> TAG TO CHANGE PAGES.
					// Game Loop
					myLevel.startGame(); // This will start the game loop after initializing or setting the scene.
				}
			}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try {
			goToLevel((String) evt.getNewValue());// The real manager.
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getCause().toString());
			alert.show();
		}
	}
}


//@Override
//	// This method gets called by default if the observed object's setChanged() fx is called.
//	public void update(Observable arg0, Object arg1) {
//		try {
//			goToLevel((String) arg1);// The real manager.
//		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
//				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			Alert alert = new Alert(AlertType.ERROR);
////			alert.setContentText(e.getClass().toString());
//			alert.setContentText(e.getCause().toString());
//			alert.show();
//		}
//	}