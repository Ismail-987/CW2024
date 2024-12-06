package com.example.demo.utilities;

/**
 * Utility class for storing static data used in the game.
 */
public class DataUtilities {

    // Level scene identifiers
    public static String LevelOne = "com.example.demo.scenes.LevelOne";
    public static String LevelTwo = "com.example.demo.scenes.LevelTwo";
    public static String LevelThree = "com.example.demo.scenes.LevelThree";
    public static String LevelFinal = "com.example.demo.scenes.LevelFinal";
    public static String HomeScene = "com.example.demo.scenes.HomeScene";

    // Level names
    public static String LevelOneName = "EARTH";
    public static String LevelTwoName = "MARS";
    public static String LevelThreeName = "JUPITER";
    public static String LevelFinalName = "FINAL BOSS";

    // Level numbers
    public static int LevelOneNumber = 1;
    public static int LevelTwoNumber = 2;
    public static int LevelThreeNumber = 3;
    public static int LevelFinalNumber = 4;

    // Background images paths
    public static String LevelOneBackgroundImage = "/com/example/demo/images/level1Background.jpg";
    public static String LevelTwoBackgroundImage = "/com/example/demo/images/level2Background.jpg";
    public static String LevelThreeBackgroundImage = "/com/example/demo/images/background1.jpg";
    public static String LevelFinalBackgroundImage = "/com/example/demo/images/finalLevelBackground.jpg";
    public static String HomeMenuBackgroundImage = "/com/example/demo/images/homescreen.jpg";
    public static String HomeBackgroundImage = "/com/example/demo/images/homescreenbackground2.jpg";

    // Number of kills required per level
    public static int LevelOneNumberOfKills = 4;
    public static int LevelTwoNumberOfKills = 5;
    public static int LevelThreeNumberOfKills = 10;

    // Player health per level
    public static int LevelOnePlayerHealth = 5;
    public static int LevelTwoPlayerHealth = 5;
    public static int LevelThreePlayerHealth = 4;
    public static int LevelFinalPlayerHealth = 3;

    // Enemy spawn probabilities per level
    public static double LevelOneEnemySpawnProbability = 0.02;
    public static double LevelTwoEnemySpawnProbability = 0.1;
    public static double LevelThreeEnemySpawnProbability = 0.3;

    // Total enemies per level
    public static int LevelOneTotalEnemies = 5;
    public static int LevelTwoTotalEnemies = 7;
    public static int LevelThreeTotalEnemies = 8;

    // Music file paths for levels and home screen
    public static String LevelOneMusic = "/com/example/demo/images/level1music.mp3";
    public static String LevelTwoMusic = "/com/example/demo/images/level1music.mp3";
    public static String LevelThreeMusic = "/com/example/demo/images/level1music.mp3";
    public static String LevelFinalMusic = "/com/example/demo/images/level1music.mp3";
    public static String HomeMusic = "/com/example/demo/images/homemenuviewmusic.mp3";

    // Music file paths for win/lose scenarios
    public static String YouWinMusic = "/com/example/demo/images/youwinmusic.mp3";
    public static String YouLoseMusic = "/com/example/demo/images/youLostSound.mp3";

    // Screen dimensions and game properties
    public static int ScreenHeight = 750;
    public static int ScreenWidth = 1300;
    public static String GameTitle = "SKY BATTLE";

    // Game status file path
    public static String GameFile = "src/main/resources/gameStatus/gameStatus.txt";

    // Timer and screen adjustments
    public static int MILISECOND_DELAY = 50;
    public static int SCREENHEIGHTADJUSTMENT = 170;
    public static int GAME_SCREENS_X_POSITION = 355;
    public static int GAME_SCREENS_Y_POSITION = 170;

    public static int MAX_FRAMES_FOR_POWER_UP = 300;
    public static int frameswithPowerUp = 0;
    public static double BUTTON_PROBABILITY = 0.2;
    public static int MAX_FRAMES_FOR_BUTTON = 100;
    public static int frameswithButton = 0;

    public static Boolean isPowerUpActive = false;
    public static Boolean isButtonActive = false;

    /**
     * Default constructor.
     */
    public DataUtilities() {
    }
}