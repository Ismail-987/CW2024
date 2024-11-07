package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private static final String LEVEL_TWO_CLASS_NAME = "com.example.demo.LevelTwo";
	private final Stage stage;

	// Constructor
	public Controller(Stage stage) {

		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);

	}

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			// Create Class Object that represent ClassName
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth()); // Create Level 1 Screen or Page
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene(); // Web page for level 1.
			stage.setScene(scene);
			// Game Loop
			myLevel.startGame(); // This will start the game loop after initializing or setting the scene.

	}

	@Override
	// This method gets called by default if the observed object's setChanged() fx is called.
	public void update(Observable arg0, Object arg1) {
		try {
			goToLevel(LEVEL_TWO_CLASS_NAME);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
//			alert.setContentText(e.getClass().toString());
			alert.setContentText(e.getCause().toString());
			alert.show();
		}
	}

}
