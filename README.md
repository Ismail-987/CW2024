# SOFTWARE MAINTENANCE (20612966)

## DESCRIPTION
This project involves the maintenance and extension of a game software
* Game 1

## GITHUB REPOSITORY
* https://github.com/Ismail-987/CW2024.git

## COMPILATION INSTRUCTIONS

## ADDED DEPENDENCIES
1. java.desktop package in module com.example.demo in module-info.java.

## FEATURES IMPLEMENTED 

### THAT SUCCESSFULLY WORK PROPERLY
* Fixed the failure to transition to second level.
* Created a home screen to welcome the Gamers to our game.
* Created a pause screen to allow users to have a navigation to other sections of the game.
* Users can pause by clicking the pause button or pressing "P" on keyboard.
* Users can resume the game by clicking play button in the pause screen.

### THAT DO NOT SUCCESSFULLY WORK PROPERLY
* Resizing the stage. Unfortunately user can resize the stage but aspect ratio is not feasible.
 Perhaps if I could more time for the game, I could have make it a reality.

## FEATURES NOT IMPLEMENTED

## JAVA CLASSES

### NEW CLASSES
1. PauseButton.java
2. PlayButton.java
3. RestartButton.java
4. HomeScreen.java
5. PauseScene.java
6. StageManager.java (Used to be Controller.java)


### MODIFIED CLASSES
1. LevelParent.java
* Restructured in a logical Order for ease of programming access.
2. Boss.java
* Changed the IMAGE_NAME attribute to match the correct format for importing images.
3. SheildImage.java
* SHIELD_IMAGE attribute is changed from .jpg to .png.
4. Controller Class
* Changed the notification system from Observer Pattern to PropertyChangeListener. 
 This is because the Observer Class is no longer supported in Java (Deprecated).
* Changed its name to StageManager.java for convenience.
5. ActiveActorDestructible.java
* This class has been deleted from the code as its functionally not relevant.
 This lowers the code base.
6. 



## UNCERTAINTIES
