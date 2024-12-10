package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;
import com.example.demo.utilities.DataUtilities;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The entry point for the game application, initializing and launching the game.
 */
public class Main extends Application {

	private StageManager myController;

	public void setMyController(StageManager myController) {
		this.myController = myController;
	}

	public Main(StageManager stageManager) {
		this.myController = stageManager;
	}
	public Main() {
	}

	/**
	 * Starts the JavaFX application, setting up the main stage and launching the game.
	 *
	 * @param stage The primary stage for this application.
	 * @throws ClassNotFoundException    if a required class cannot be found.
	 * @throws NoSuchMethodException     if a required method cannot be found.
	 * @throws SecurityException         if a security violation occurs.
	 * @throws InstantiationException    if an error occurs during class instantiation.
	 * @throws IllegalAccessException    if the method cannot be accessed.
	 * @throws InvocationTargetException if the method invocation fails.
	 */
	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		stage.setTitle(DataUtilities.GameTitle);
		stage.setResizable(true);
		stage.setHeight(DataUtilities.ScreenHeight);
		stage.setWidth(DataUtilities.ScreenWidth);

		if (myController == null) {
			myController = new StageManager(stage);
		}
		myController.launchGame();
	}

	/**
	 * The main method serving as the application's entry point.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch();
	}
}