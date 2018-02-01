package pl.edu.pwr;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pl.edu.pwr.engine.Parameters;
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

    private ReadOnlyIntegerProperty generation;
    private Simulation simulation;
    public boolean fastForwardOnFinish = false;

    public EntityStepTimer(Simulation simulation, long interval, long duration) {
        super(interval, duration);
        this.simulation = simulation;
        generation = new SimpleIntegerProperty();
        resetGeneration();
    }

    public final int getGeneration() {
        return generation.get();
    }

    public final void incrementGeneration() {
        ((SimpleIntegerProperty) generation).set(getGeneration() + 1);
    }

    public final void resetGeneration() {
        ((SimpleIntegerProperty) generation).set(1);
    }

    public ReadOnlyIntegerProperty generationProperty() {
        return generation;
    }

    @Override
    protected void onTick() {
        if (Parameters.score && getGeneration() == 1000) {
            Dumper.dumpPopulation(getGeneration(), simulation);
        }

        if (!simulation.simulate()) {
            cancel();
            onFinish();
        }
    }

    @Override
    protected void onFinish() {

        if (Parameters.score) {
            Dumper.dumpData(getGeneration(), simulation);
            Dumper.flushPopulation();
        }

        LocalTime start = LocalTime.now();
        List<Entity> newHerbivorePopulation = new GeneticAlgorithm(simulation.getHerbivoresGenotypes()).run().stream()
                .map(x -> EntityFactory.getEntity(EntityType.HERBIVORE, x)).collect(Collectors.toList());
        List<Entity> newCarnivorePopulation = new GeneticAlgorithm(simulation.getCarnivoresGenotypes()).run().stream()
                .map(x -> EntityFactory.getEntity(EntityType.CARNIVORE, x)).collect(Collectors.toList());
        LocalTime end = LocalTime.now();
        System.out.println("Finished GA: " + ((end.getNano() - start.getNano()) / 1000000) + " ms");
        this.simulation = new Simulation(newHerbivorePopulation, newCarnivorePopulation);
        stopFastForward();
        start();
        if (fastForwardOnFinish) {
            startFastForward();
        }

        // Finish after 200 generations
        if (getGeneration() == 1000) {
            reset();
            cancel();
        }
        incrementGeneration();
    }

    @Override
    protected void onReset() {
        start();
    }

    public Simulation getSimulation() {
        return simulation;
    }
}
