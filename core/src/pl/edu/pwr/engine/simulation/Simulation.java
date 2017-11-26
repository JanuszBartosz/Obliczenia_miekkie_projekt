package pl.edu.pwr.engine.simulation;

import pl.edu.pwr.engine.Parameters;
import pl.edu.pwr.graphics.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulation {

    List<Entity> plants;
    List<Entity> herbivores;
    List<Entity> deadHerbivores;
    List<Entity> carnivores;

    public Simulation() {
        this.plants = Stream.generate(() -> EntityFactory.getEntity(EntityType.PLANT)).limit(Parameters.numberPlants).collect(Collectors.toList());
        this.herbivores = Stream.generate(() -> EntityFactory.getEntity(EntityType.HERBIVORE)).limit(Parameters.numberHerbivores).collect(Collectors.toList());
        this.carnivores = Stream.generate(() -> EntityFactory.getEntity(EntityType.CARNIVORE)).limit(Parameters.numberCarnivores).collect(Collectors.toList());
    }

    public void simulate() {

        List<Entity> plantsToRemove;

        for (Entity carnivore : carnivores) {
            //Check if caught pray.
            if (foundFood) {
                carnivore.incrementFoundFood();
            }
            Entity nearestHerbivore = findNearestEntity(carnivore, herbivores);
        }

        for (Entity herbivore : herbivores) {
            //Check if found plant.
            if (foundFood) {
                herbivore.incrementFoundFood();
            }
            Entity nearestPlant = findNearestEntity(herbivore, plants);
            Entity nearestCarnivore = findNearestEntity(herbivore, carnivores);
        }
    }

    public Entity findNearestEntity(Entity entity, List<Entity> entities) {

        float minDistance = Float.MAX_VALUE;
        Entity closestEntity = null;
        float x = entity.getX();
        float y = entity.getY();

        for (Entity entityB : entities) {
            float distance = (float) Math.sqrt(Math.pow(entityB.getX() - x, 2) + Math.pow(entityB.getY() - y, 2));
            if (distance < minDistance) {
                minDistance = distance;
                closestEntity = entityB;
            }
        }
        return closestEntity;
    }
}
