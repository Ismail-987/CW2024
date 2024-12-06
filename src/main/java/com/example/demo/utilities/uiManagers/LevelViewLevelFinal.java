package com.example.demo.utilities.uiManagers;

import com.example.demo.UIObjects.Images.figures.ShieldImage;
import com.example.demo.utilities.GameState;
import javafx.scene.Group;

/**
 * Manages the level view for the final level, including shield management.
 */
public class LevelViewLevelFinal extends LevelView {

	private static final int SHIELD_X_POSITION = 880;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;

	/**
	 * Constructs a LevelViewLevelFinal with root node, game state, and a callback to go to the home screen.
	 *
	 * @param root           The root group for the level view.
	 * @param gameState      The current state of the game.
	 * @param goToHomeScreen Runnable to execute when transitioning to the home screen.
	 */
	public LevelViewLevelFinal(Group root, GameState gameState, Runnable goToHomeScreen) {
		super(root, gameState, goToHomeScreen);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
	}

	/**
	 * Displays the shield on the screen.
	 */
	public void showShield() {
		shieldImage.setVisible(true);
	}

	/**
	 * Hides the shield from the screen.
	 */
	public void hideShield() {
		shieldImage.setVisible(false);
	}



	/**
	 * Initializes and adds the shield component to the root group.
	 */
	@Override
	public void initializeShield() {
		root.getChildren().add(shieldImage);
	}

	/**
	 * Returns the shield image.
	 *
	 * @return The ShieldImage instance.
	 */
	@Override
	public ShieldImage getShield() {
		return shieldImage;
	}
}