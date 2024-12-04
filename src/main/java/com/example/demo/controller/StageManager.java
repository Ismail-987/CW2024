package com.example.demo.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import com.example.demo.scenes.LevelOne;
import com.example.demo.scenes.HomeScene;
import com.example.demo.utilities.DataUtilities;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.scenes.LevelParent;

/**
 * Manages scene transitions and lifecycle events for the game stages.
 */
public class StageManager implements PropertyChangeListener {

	private final Stage stage;
	private LevelParent myLevel = new LevelOne();
	private HomeScene homeScene = new HomeScene(10, 20);

	/**
	 * Constructs a StageManager for handling the specified stage.
	 *
	 * @param stage The main stage for the application.
	 */
	public StageManager(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Launches the game, displaying the initial home scene.
	 *
	 * @throws ClassNotFoundException if the class for a specified scene is not found.
	 * @throws NoSuchMethodException  if the constructor for a scene cannot be found.
	 * @throws SecurityException      if a security manager denies access.
	 * @throws InstantiationException if an error occurs during instantiation.
	 * @throws IllegalAccessException if the constructor is inaccessible.
	 * @throws InvocationTargetException if the underlying method throws an exception.
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		stage.show();
		goToScene(DataUtilities.HomeScene);
	}

	/**
	 * Transitions to the specified scene.
	 *
	 * @param className The name of the class representing the scene to transition to.
	 * @throws ClassNotFoundException if the class for a specified scene is not found.
	 * @throws NoSuchMethodException  if the constructor for a scene cannot be found.
	 * @throws SecurityException      if a security manager denies access.
	 * @throws InstantiationException if an error occurs during instantiation.
	 * @throws IllegalAccessException if the constructor is inaccessible.
	 * @throws InvocationTargetException if the underlying method throws an exception.
	 */
	private void goToScene(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		if (Objects.equals(className, DataUtilities.HomeScene)) {
			if (homeScene.exists) {
				homeScene.getHomeScreenMusic().play();
				stage.setScene(homeScene.returnScene());
			}
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class);
			homeScene = (HomeScene) constructor.newInstance(stage.getHeight(), stage.getWidth());
			homeScene.exists = true;
			homeScene.getSupport().addPropertyChangeListener(this);
			homeScene.getHomeScreenMusic().play();
			Scene scene = homeScene.returnScene();
			stage.setScene(scene);
		} else {
			if (myLevel.exist) {
				stage.setScene(myLevel.getScene());
			} else {
				Class<?> myClass = Class.forName(className);
				Constructor<?> constructor = myClass.getConstructor();
				myLevel = (LevelParent) constructor.newInstance();
				myLevel.getSupport().addPropertyChangeListener(this);
				Scene scene = myLevel.initializeScene();
				try {
					scene.getStylesheets().add(getClass().getResource("/game-styles.css").toExternalForm());
				} catch (NullPointerException e) {
					System.err.println("CSS file not found! Ensure the file is in the correct location.");
				} finally {
					stage.setScene(scene);
					myLevel.startGame();
				}
			}
		}
	}

	/**
	 * Handles property change events to switch scenes when notified.
	 *
	 * @param evt The PropertyChangeEvent providing details for the change.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try {
			goToScene((String) evt.getNewValue());
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			Throwable cause = e.getCause();
			String message = (cause != null) ? cause.toString() : "Unknown error";
			alert.setContentText(message);
			alert.show();
		}
	}
}