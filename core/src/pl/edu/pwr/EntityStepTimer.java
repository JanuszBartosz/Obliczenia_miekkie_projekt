package pl.edu.pwr;

import pl.edu.pwr.engine.simulation.EntityFactory;
import pl.edu.pwr.engine.simulation.EntityType;
import pl.edu.pwr.engine.simulation.Simulation;
import pl.edu.pwr.engine.training.GeneticAlgorithm;
import pl.edu.pwr.graphics.Entity;

import java.time.Clock;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class EntityStepTimer extends ForwardableTimer {

    private Simulation simulation;

    public EntityStepTimer(Simulation simulation, long interval, long duration) {
        super(interval, duration);
        this.simulation = simulation;
    }

    @Override
    protected void onTick() {
        simulation.simulate();
    }

    @Override
    protected void onFinish() {
        LocalTime start = LocalTime.now();
        List<Entity> newHerbivorePopulation = new GeneticAlgorithm(simulation.getHerbivoresGenotypes()).run().stream()
                .map(x -> EntityFactory.getEntity(EntityType.HERBIVORE, x)).collect(Collectors.toList());
        List<Entity> newCarnivorePopulation = new GeneticAlgorithm(simulation.getCarnivoresGenotypes()).run().stream()
                .map(x -> EntityFactory.getEntity(EntityType.CARNIVORE, x)).collect(Collectors.toList());
        LocalTime end = LocalTime.now();
        System.out.println("Finished GA: " + (end.getNano() - start.getNano()));
        this.simulation = new Simulation(newHerbivorePopulation, newCarnivorePopulation);
        start();
    }

    @Override
    protected void onReset() {
        start();
    }

    public Simulation getSimulation() {
        return simulation;
    }
}
