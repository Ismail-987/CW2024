# SOFTWARE MAINTENANCE (20612966)

## DESCRIPTION
This project involves the maintenance and extension of a game software.


## GITHUB REPOSITORY
* https://github.com/Ismail-987/CW2024.git

## CONFIGURATION INSTRUCTIONS
1. Upon opening the game in any IDE, in command line run "mvn clean" then "mvn install"  
 for maven to fetch the added dependencies in pom.xml.
2. Other note: If packages are not seen in JavaDoc html site, please go to "TREE" section to see the class and package hierarchy.


## ADDED DEPENDENCIES
1. javafx-media - Need to check if module-info.java has - "requires javafx.media" package added.
2. java.desktop package in module com.example.demo in module-info.java.
3. mockito. This is used in testing.

## FEATURES IMPLEMENTED 

### THAT SUCCESSFULLY WORK PROPERLY
1. HOME NAVIGATION
* Created a home screen to welcome Gamers to our game. It has :-
* Home Screen Menu. User can have a basic navigation around the Home menu.

2. PAUSE AND PLAY FUNCTIONALITY
* Users can pause by clicking the pause button on the top right or pressing "P" on keyboard.
* This will direct them to a pause screen.
* Users can resume the game by clicking play button in the pause screen.

3. SHIELD FUNCTIONALITY
* Showing shield. The boss's shield is now active and working as expected.

4. UPON WINNING AND LOSING
* After completing a level, user is shown a win screen for further navigation.
* After a lost, loose sound is played and "You Lost" screen is shown.
 Provides user with multiple functionalities like skipping level or tips.

5. SOUND AND MUSIC BACKGROUNDS
* Added background music for various levels.
* Added music background to pause menu.
* Added sounds/ musics upon losing and winning.
* Added projectile sound system.

5. SAVING
* The game allows user to save the last level that the user reached.
 Upon clicking "SAVE" buttons in screens like PauseGame and YouWin game,
 the game will save the level checkpoint. Upon clicking "CONTINUE" button 
 in HomeScreen in HomeScene, the game will load the level next to the last 
 level that the user saved, continuing the progress.
* Upon pausing, the save button will save the current level that the user
 is playing. Hence, the continue button will direct to the current level of the 
 game.

6. SCORE TRACKING AND LEVEL DISPLAY
* The game has the facilities for tracking the score count upon every kill.
 It informs the user of how long to go to finish a particular level.
* The game also displays what level is it to the user so that the user can 
 know where he or she is.

7. MULTI-LEVEL FUNCTIONALITY
* The game supports multiple levels of up to 4. 
* In each level, you pass through different worlds where you fight the enemies of the 
 final boss who lives on Planet "PHOENIX-365" to save the UNIVERSE. 
* The player first goes from Earth, destroys the enemies. Then attempts 
 another revenge by travelling to MARS and kills the enemies of that place. Then,
 travels to JUPITER and finishes the enemies off. Then to realize that their boss lives on 
 planet "PHOENIX-365". He then prepares well and face the final Boss and save
 the UNIVERSE.

8. SCORE-BOOST FUNCTIONALITY
* This feature is available in each level where the user is presented
 with a "SCORE X2" Button that if he or she is lucky enough to press it,
 he or she will have "DOUBLE" increased kill count, thus having more score per kill.
 For the final level, this button increases score count per projectile damage.
* This feature, if gotten, only lasts for a couple of times and becomes expired. This is until
 another lucky shot of getting to click the "SCORE X2" button again.

9. HEALTH-BOOST FUNCTIONALITY
* This feature is available in each level where the user is presented with
 a "HEALTH +1" button that if pressed, he or she will get an INCREMENT increase in 
 health. This button appears randomly in any 
 position on the screen in a random probability if the user's health is below "3 HEARTS"

### THAT DO NOT SUCCESSFULLY WORK PROPERLY
* STAGE RESIZING. Unfortunately user can resize the stage but aspect ratio is not feasible.
 Perhaps if I could more time for the game, I could have make it a reality.
* BUTTONS: Some button functionalities like in Settings Page (The one for sound) and in pause screen,
 the one for tips are not behaving as expected. The project was too overwhelming that finishing all the 
 functionalities did not seem feasible. Unfortunately.

## FEATURES NOT IMPLEMENTED


## JAVA CLASSES

### NEW CLASSES
1. PauseButton.java
2. SettingsScene.java
3. InformationScene.java
4. HomeScene.java
5. PauseScreen.java
6. StageManager.java (Used to be Controller.java)
7. WinScreen.java
8. PauseScreen.java
9. LevelFinal.java (Used to be LevelTwo.java) + LevelViewLevelFinal.java
   (Used to be LevelViewLevelTwo.java)
10. LevelTwo.java + LevelViewLevelTwo.java
11. LevelThree.java + LevelViewLevelThree.java
12. DataUtilities.java
13. FileUtility.java
14. NavigationUtilities.java
15. CollisionManager.java
16. LoseScreen.java
17. GameBackground.java
18. GameFinishedScreen.java
19. LevelViewLevelOne.java
20. GameState.java


### MODIFIED CLASSES
* Almost All classes have been modified to some degree to 
 enhance the functionality of the game.


## PACKAGES
### NEW PACKAGES
1. com.example.demo.factories
2. com.example.demo.scenes
3. com.example.demo.UIObjects
4. com.example.demo.UIObjects.Containers
5. com.example.demo.UIObjects.Images
6. com.example.demo.UIObjects.Images.actors
7. com.example.demo.UIObjects.Images.figures
8. com.example.demo.utilities
9. com.example.demo.utilities.uiManagers

### MODIFIED PACKAGES

## NEW FILES
1. game-styles.css
2. gameStatus.txt (generated dynamically during initial save)

## REFACTORING
* Code refactoring has been done to ensure better performance of the 
 game. These include the following:-
1. SEPARATION OF CONCERNS
* Classes like LevelParent.java and other big classes were too long and had a lot of responsibilities 
 combined. Further splits of responsibilities were done leading to the creation of the 
 package called "utilities". This package manages Majority of the functions found in the gameLoop Thread
 and others.

2. DOCUMENTATION
* Further documentation was added to the code for better code readability.
 The comments added reflects the comments in the javaDoc.
* Class Diagram: The class diagram is present to show visual representation of the 
 game system. This aids in game code interpretation and analysis.

3. DESIGN PATTERNS
* OBSERVER PATTERN: A new implementation of the observer pattern using PropertyChangeListener is applied
 in StageManager.java. This Specific implementation is necessary for further development as 
 it is not deprecated by java community developers.
* FACTORY PATTERN: This pattern is applied by the use of 
 uiManagers in utilities class. These managers ensures the creation of various UI components
 and some of the logic to manage them. Further management of the UI components 
 is done in GameState.java as this class deals with main Game Thread running in every game Loop.
* SINGLETON PATTERN: This pattern has been implemented but in the form of having only one
 instance and passing it as an argument to other functions and other classes as reference.
 A typical example of this is the use of GameState object of that particular instance is used in 
 multiple classes to complete the logic of the host class.

4. TESTING
* The testing done on the project is based on the criteria of 
 frequency of usage of classes and their respective methods.
 This is because the more the class or method is used, the more relevant it becomes.
 Thus it is wise to test the most used classes as they cover more 
 project scope.
* Thus based on this, the below are the classes chosen:-

* i. HIGH-LEVEL CLASSES: Like LevelParent, LevelView and ActiveActor are chosen
 for testing as high-level classes are more like to be used. Thus their logic should
 be tested.
* ii. UTILITY CLASSES: Utility classes found in utilities packages like GameState.java, 
 NavigationUtilities.java and FileUtilities.java are also tested due to their scope of usage in the game.
* iii. MANAGER CLASSES: Manager classes like Main.java and StageManager.java are also tested 
 as they envelope the whole project scope. Thus testing to see if they work will
 be vital for confidentiality of the project as a whole.

## UNCERTAINTIES
1. Lack of enough experience with application of Design Principles and Patterns.
- It was extremely challenging to try to implement SOLID principles
 and various patterns for such a large code base. Had I had more 
 exposure and experience, better results would have been attained, I believe.
2. Graphics designing. It was a challenging experience to have consistent screens for various scenes.
 This is because of low experience with graphics designing and usage of graphics tools.
3. Testing. User Interface Testing was a very challenging task as UI figures runs on a separate 
 thread. Thus requiring "MOCKING TESTING" and many other configuration settings to ensure it works.
