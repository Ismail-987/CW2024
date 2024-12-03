package com.example.demo.utilities;

import com.example.demo.UIObjects.Images.actors.ActiveActor;
import javafx.scene.Group;

import java.util.List;
import java.util.stream.Collectors;

public class CollisionManager {
    public CollisionManager(){

    }
    public static void collisionManager(List<ActiveActor> friendlyUnits, List<ActiveActor>EnemyUnits, List<ActiveActor>UserProjectiles, List<ActiveActor>EnemyProjectiles, Group root) {

        handleCollisions(UserProjectiles, EnemyUnits);
        handleCollisions(EnemyProjectiles, friendlyUnits);
        handleCollisions(friendlyUnits, EnemyUnits);

        removeDestroyedActors(friendlyUnits,root);
        removeDestroyedActors(EnemyProjectiles,root);
        removeDestroyedActors(UserProjectiles,root);
        removeDestroyedActors(EnemyUnits,root);
    }

    public static void handleCollisions(List<ActiveActor> actors1,
                                  List<ActiveActor> actors2) {
        for (ActiveActor actor : actors2) {
            for (ActiveActor otherActor : actors1) {
                if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
                    actor.takeDamage();
                    otherActor.takeDamage();
                }
            }
        }

    }


    public static void removeDestroyedActors(List<ActiveActor> actors, Group root) {
        List<ActiveActor> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
                .collect(Collectors.toList());
        root.getChildren().removeAll(destroyedActors);
        actors.removeAll(destroyedActors);
    }
}
