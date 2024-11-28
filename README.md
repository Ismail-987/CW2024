# SOFTWARE MAINTENANCE (20612966)

## DESCRIPTION
This project involves the maintenance and extension of a game software
* Game 1

## GITHUB REPOSITORY
* https://github.com/Ismail-987/CW2024.git

## COMPILATION INSTRUCTIONS
1. Upon opening the game in any IDE, in command line run "mvn install"  
 for maven to fetch the added dependencies in pom.xml. 

## ADDED DEPENDENCIES
1. javafx-media - Need to check if module-info.java has - "requires javafx.media" package added.
2. java.desktop package in module com.example.demo in module-info.java.

## FEATURES IMPLEMENTED 

### THAT SUCCESSFULLY WORK PROPERLY
1. HOME NAVIGATION
* Created a home screen to welcome Gamers to our game. It has :-
* Home Screen Menu. User can have a basic navigation around the Home menu.

2. PAUSE AND PLAY FUNCTIONALITY
* Users can pause by clicking the pause button on the top right or pressing "P" on keyboard.
* This will direct them to a pause screen.
* Users can resume the game by clicking play button in the pause screen.
* 

3. SHIELD FUNCTIONALITY
* Showing shield. The boss's shield is now active and working as expected.

4. UPON WINNING AND LOSING
* After completing a level, user is shown a win screen for further navigation.
* After a lost, loose sound is played and "You Lost" screen is shown.
 Provides user with multiple functionalities like skipping level or tips.

5. SOUND AND MUSIC BACKGROUNDS
* Added background music for various levels.
* Added the background to pause menu.
* Added sounds/ musics upon losing and winning.
* Added projectile sound system.

### THAT DO NOT SUCCESSFULLY WORK PROPERLY
* Resizing the stage. Unfortunately user can resize the stage but aspect ratio is not feasible.
 Perhaps if I could more time for the game, I could have make it a reality.
* NAVIGATION BUTTONS. Some navigation buttons in some screens will not behave as expected.
 Unfortunately, it became overwhelming to finish all the functionality but had it been with more time,
 they would have work as expected.

## FEATURES NOT IMPLEMENTED


## JAVA CLASSES

### NEW CLASSES
1. PauseButton.java
2. PlayButton.java
3. RestartButton.java
4. HomeScene.java
5. PauseScreen.java
6. StageManager.java (Used to be Controller.java)
7. WinScreen.java
8. PauseScreen.java


### MODIFIED CLASSES
1. LevelParent.java
* Restructured in a logical Order for ease of programming access.
2. Boss.java
* Changed the IMAGE_NAME attribute to match the correct format for importing images.
* Added projectile sound system for boss projectiles
3. SheildImage.java
* SHIELD_IMAGE attribute is changed from .jpg to .png.
4. Controller Class
* Changed the notification system from Observer Pattern to PropertyChangeListener. 
 This is because the Observer Class is no longer supported in Java (Deprecated).
* Changed its name to StageManager.java for convenience.
5. ActiveActorDestructible.java
* This class has been deleted from the code as its functionality can be combined in a single super class.
 This lowers the code base.
6. LevelOne.java
* Added Win Screen for better interactivity.

## PACKAGES
### NEW PACKAGES
1. com.example.demo.factories
2. com.example.demo.scenes
3. com.example.demo.UIObjects
4. com.example.demo.UIObjects.Containers
5. com.example.demo.UIObjects.Images
6. com.example.demo.UIObjects.Images.actors
7. com.example.demo.UIObjects.Images.figures

### MODIFIED PACKAGES

## UNCERTAINTIES
1. Lack of enough experience with application of Design Principles and Patterns.
- It was extremely challenging to try to implement SOLID principles
 and various patterns for such a large code base. Had I had more 
 exposure and experience, better results would have been attained, I believe.
2. Graphics designing. It was a challenging experience to have consistent screens for various scenes.
 This is because of low experience with graphics designing and usage of graphics tools.
