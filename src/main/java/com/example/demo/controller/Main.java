package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.utilities.DataUtilities;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private StageManager myController;

	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(DataUtilities.GameTitle);
		stage.setResizable(true);
		stage.setHeight(DataUtilities.ScreenHeight);
		stage.setWidth(DataUtilities.ScreenWidth);
		myController = new StageManager(stage);
		myController.launchGame();
	}

	public static void main(String[] args) {

		launch();
	}
}