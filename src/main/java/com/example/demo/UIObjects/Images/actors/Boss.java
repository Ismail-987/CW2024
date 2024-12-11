package com.example.demo.UIObjects.Images.actors;

import com.example.demo.utilities.DataUtilities;

import java.util.*;

/**
 * Represents a boss enemy with special abilities, such as firing projectiles and activating shields.
 */
public class Boss extends FighterPlane {

	private static final String IMAGE_NAME = "bossplane1.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = .02;
	private static final int IMAGE_HEIGHT = 90;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 20;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = 40;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MAX_FRAMES_WITH_SHIELD = 60;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	/**
	 * Constructs a Boss with default settings.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	/**
	 * Updates the position of the boss following a predefined movement pattern.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}

	/**
	 * Updates the boss' state, including its position and shield status.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile in the current frame if certain conditions are met.
	 *
	 * @return a new BossProjectile if firing occurs, otherwise null.
	 */
	@Override
	public Projectile fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Applies damage to the boss if not shielded.
	 * Also checks if the powerUp feature active and
	 * activate the power-up damage.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	/**
	 * Initializes the movement pattern of the boss.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield status of the boss.
	 */
	private void updateShield() {
		if (isShielded) {
			framesWithShieldActivated++; // Per frame
		} else if (shieldShouldBeActivated()) {// Check if the power-up active?
			activateShield();
		}
		if (shieldExhausted()) {
			deactivateShield();
		}
	}


	/**
	 * Determines the next movement increment for the boss.
	 *
	 * @return the movement value from the current movement pattern.
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines if boss fires in the current frame.
	 *
	 * @return true if the boss should fire, otherwise false.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Calculates the initial Y position for projectiles.
	 *
	 * @return the Y position for new projectiles.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	/**
	 * Determines if the shield should activate based on probability.
	 *
	 * @return true if the shield should activate, otherwise false.
	 */
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

	/**
	 * Checks if the shield's activation time is exhausted.
	 *
	 * @return true if the shield time is exhausted, otherwise false.
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the boss' shield.
	 */
	private void activateShield() {
		isShielded = true;
	}

	/**
	 * Deactivates the boss' shield.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	/**
	 * Checks if the boss is currently shielded.
	 *
	 * @return true if shielded, otherwise false.
	 */
	@Override
	public Boolean isShielded() {
		return isShielded;
	}

	/**
	 * Returns the initial health of the boss.
	 *
	 * @return the initial health value.
	 */
	public int getInitHealth() {
		return HEALTH;
	}
}