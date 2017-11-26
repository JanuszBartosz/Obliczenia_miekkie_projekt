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
    List<Entity> carnivores;

    public Simulation() {
        this.plants = Stream.generate(() -> EntityFactory.getEntity(EntityType.PLANT)).limit(Parameters.numberPlants).collect(Collectors.toList());
        this.herbivores = Stream.generate(() -> EntityFactory.getEntity(EntityType.HERBIVORE)).limit(Parameters.numberHerbivores).collect(Collectors.toList());
        this.carnivores = Stream.generate(() -> EntityFactory.getEntity(EntityType.CARNIVORE)).limit(Parameters.numberCarnivores).collect(Collectors.toList());
    }
}
