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

public class StageManager implements PropertyChangeListener {

	private final Stage stage;
	private  LevelParent myLevel = new LevelOne();
	private HomeScene homeScene = new HomeScene(10,20);


	public StageManager(Stage stage) {

		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToScene(DataUtilities.HomeScene);

	}

	private void goToScene(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

			if(Objects.equals(className, DataUtilities.HomeScene)){

				if(homeScene.exists){
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
 			}
			else {

				if (myLevel.exist) {
					stage.setScene(myLevel.getScene());
				}
				else {
					Class<?> myClass = Class.forName(className);
					Constructor<?> constructor = myClass.getConstructor();
					myLevel = (LevelParent) constructor.newInstance();
					myLevel.getSupport().addPropertyChangeListener(this);
					Scene scene = myLevel.initializeScene();

					// NEED A FILE FOR STYLING THE COMPONENTS OF THE SCENE.
					try {
						scene.getStylesheets().add(getClass().getResource("/game-styles.css").toExternalForm());
					}
					catch (NullPointerException e) {
						System.err.println("CSS file not found! Ensure the file is in the correct location.");
					}
					finally {
						stage.setScene(scene);
						myLevel.startGame();
					}

				}
			}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		try {
			goToScene((String) evt.getNewValue());
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				 | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			Throwable cause = e.getCause();
			String message = (cause != null) ? cause.toString() : "Unknown error";
			alert.setContentText(message);
			alert.show();
		}
	}
}


