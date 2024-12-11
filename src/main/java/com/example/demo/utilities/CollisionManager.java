package com.example.demo.utilities;

import com.example.demo.UIObjects.Images.actors.ActiveActor;
import javafx.scene.Group;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages and processes collisions among game actors.
 */
public class CollisionManager {

    /**
     * Constructs a new CollisionManager instance.
     */
    public CollisionManager() {
        // Default constructor
    }

    /**
     * Processes collisions between different game actors and projectiles.
     *
     * @param friendlyUnits list of friendly units in the game
     * @param enemyUnits list of enemy units in the game
     * @param userProjectiles projectiles launched by the user
     * @param enemyProjectiles projectiles launched by enemies
     * @param root the root node for managing scene objects
     */
    public static void collisionManager(List<ActiveActor> friendlyUnits, List<ActiveActor> enemyUnits, List<ActiveActor> userProjectiles, List<ActiveActor> enemyProjectiles, Group root) {
        handleCollisions(userProjectiles, enemyUnits);
        handleCollisions(enemyProjectiles, friendlyUnits);
        handleUserEnemyCollisions(friendlyUnits,enemyUnits);

        removeDestroyedActors(friendlyUnits, root);
        removeDestroyedActors(enemyProjectiles, root);
        removeDestroyedActors(userProjectiles, root);
        removeDestroyedActors(enemyUnits, root);
    }

    /**
     * Handles collision detection between two lists of actors.
     *
     * @param actors1 list of the first group of actors
     * @param actors2 list of the second group of actors
     */
    public static void handleCollisions(List<ActiveActor> actors1, List<ActiveActor> actors2) {
        for (ActiveActor actor : actors2) {
            for (ActiveActor otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }
    }

    public static void handleUserEnemyCollisions(List<ActiveActor> actors1, List<ActiveActor> actors2) {
        for (ActiveActor actor : actors2) {
            for (ActiveActor otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.destroy();
                    otherActor.destroy();
                }
            }
        }
    }

    /**
     * Removes actors that have been destroyed from both the scene and the list.
     *
     * @param actors list of actors to check for destruction
     * @param root the root node for handling scene objects
     */
    public static void removeDestroyedActors(List<ActiveActor> actors, Group root) {
        List<ActiveActor> destroyedActors = actors.stream()
                .filter(ActiveActor::isDestroyed)
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }
}