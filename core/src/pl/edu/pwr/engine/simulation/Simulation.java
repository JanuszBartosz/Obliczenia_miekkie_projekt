package pl.edu.pwr.engine.simulation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import pl.edu.pwr.engine.Parameters;
import pl.edu.pwr.graphics.Entity;
import pl.edu.pwr.graphics.RelativeEntity;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Simulation {

    List<Entity> plants;
    List<Entity> herbivores;
    List<Entity> herbivoresMouth;
    List<Entity> deadHerbivores;
    List<Entity> carnivores;
    List<Entity> carnivoresMouth;

    public Simulation() {
        this.plants = Stream.generate(() -> EntityFactory.getEntity(EntityType.PLANT)).limit(Parameters.numberPlants).collect(Collectors.toList());
        this.herbivores = Stream.generate(() -> EntityFactory.getEntity(EntityType.HERBIVORE)).limit(Parameters.numberHerbivores).collect(Collectors.toList());
        this.carnivores = Stream.generate(() -> EntityFactory.getEntity(EntityType.CARNIVORE)).limit(Parameters.numberCarnivores).collect(Collectors.toList());
        this.deadHerbivores = new ArrayList<>();
        this.herbivoresMouth = new ArrayList<>();
        this.carnivoresMouth = new ArrayList<>();

        Entity mouth = new Entity(0, 0, 0, 0, Color.BLACK, 3);
        Entity leftEye = new Entity(0, 0, 0, 0, Color.BROWN, 2);
        Entity rightEye = new Entity(0, 0, 0, 0, Color.BROWN, 2);

        for (Entity herbivore : herbivores) {
            herbivoresMouth.add(herbivore.addRelativeChild(mouth, herbivore.getRadius(), 0));
            herbivore.addRelativeChild(leftEye, herbivore.getRadius(), (float) (1.0 / 4.0 * Math.PI));
            herbivore.addRelativeChild(rightEye, herbivore.getRadius(), (float) (-1.0 / 4.0 * Math.PI));
        }

        for (Entity carnivore : carnivores) {
            carnivoresMouth.add(carnivore.addRelativeChild(mouth, carnivore.getRadius(), 0));
            carnivore.addRelativeChild(leftEye, carnivore.getRadius(), (float) (1.0 / 4.0 * Math.PI));
            carnivore.addRelativeChild(rightEye, carnivore.getRadius(), (float) (-1.0 / 4.0 * Math.PI));
        }

        herbivores.forEach(Entity::calculateChildrenPositions);
        carnivores.forEach(Entity::calculateChildrenPositions);
    }

    public synchronized void simulate() {

        Set<Entity> plantsToRemove = new HashSet<>();

        Map<Entity, Set<Entity>> intersectedPlants = Entity.getIntersectedEntities(herbivoresMouth, plants);
        Map<Entity, Set<Entity>> intersectedHerbivores = Entity.getIntersectedEntities(carnivoresMouth, herbivores);

        for (Entity carnivore : carnivores) {
            //Check if caught pray.
            Entity mouth = carnivore.getChildren().get(0);
            if (intersectedHerbivores.get(mouth) != null) {
                Set<Entity> intersections = intersectedHerbivores.get(mouth);
                carnivore.incrementFoundFood(intersections.size());
                for (Entity intersection : intersections) {
                    if (!deadHerbivores.contains(intersection)) {
                        deadHerbivores.add(intersection);
                        herbivores.remove(intersection);
                    }
                }
            }
        }

        for (Entity herbivore : herbivores) {
            //Check if found plant.
            Entity mouth = herbivore.getChildren().get(0);
            if (intersectedPlants.get(mouth) != null) {
                Set<Entity> intersections = intersectedPlants.get(mouth);
                herbivore.incrementFoundFood(intersections.size());
                plantsToRemove.addAll(intersections);
            }
        }

        plants.removeAll(plantsToRemove);

        for (int i = 0; i < Parameters.numberPlants - plants.size(); i++) {
            plants.add(EntityFactory.getEntity(EntityType.PLANT));
        }

        for (Entity carnivore : carnivores) {
            Entity nearestHerbivore = findNearestEntity(carnivore, herbivores);
            double[] inputs = new double[2];
            RelativeEntity leftEye = carnivore.getChildren().get(1);
            RelativeEntity rightEye = carnivore.getChildren().get(2);
            if (nearestHerbivore != null) {
                inputs[0] = Entity.euclideanDistance(leftEye.getX(), leftEye.getY(), nearestHerbivore.getX(), nearestHerbivore.getY());
                inputs[1] = Entity.euclideanDistance(rightEye.getX(), rightEye.getY(), nearestHerbivore.getX(), nearestHerbivore.getY());
            }else{
                inputs[0] = 0;
                inputs[1] = 0;
            }
            carnivore.setNextInputs(inputs);
        }

        for (Entity herbivore : herbivores) {
            Entity nearestPlant = findNearestEntity(herbivore, plants);
            Entity nearestCarnivore = findNearestEntity(herbivore, carnivores);
            double[] inputs = new double[4];
            RelativeEntity leftEye = herbivore.getChildren().get(1);
            RelativeEntity rightEye = herbivore.getChildren().get(2);
            inputs[0] = Entity.euclideanDistance(leftEye.getX(), leftEye.getY(), nearestPlant.getX(), nearestPlant.getY());
            inputs[1] = Entity.euclideanDistance(rightEye.getX(), rightEye.getY(), nearestPlant.getX(), nearestPlant.getY());
            inputs[2] = Entity.euclideanDistance(leftEye.getX(), leftEye.getY(), nearestCarnivore.getX(), nearestCarnivore.getY());
            inputs[3] = Entity.euclideanDistance(rightEye.getX(), rightEye.getY(), nearestCarnivore.getX(), nearestCarnivore.getY());
            herbivore.setNextInputs(inputs);
        }

        for (Entity carnivore : carnivores) {
            carnivore.makeStep();
        }

        for (Entity herbivore : herbivores) {
            herbivore.makeStep();
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

    public synchronized void drawAll(ShapeRenderer shapeRenderer) {

        for (Entity plant : plants) {
            plant.draw(shapeRenderer);
        }

        for (Entity herbivore : herbivores) {
            herbivore.draw(shapeRenderer);
        }

        for (Entity carnivore : carnivores) {
            carnivore.draw(shapeRenderer);
        }
    }
}
