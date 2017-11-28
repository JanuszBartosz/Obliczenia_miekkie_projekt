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
    private static int i = 1;
    private Simulation simulation;
    public boolean fastForwardOnFinish = false;

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
        System.out.println("\n"+ i +": onFinish()");
        LocalTime start = LocalTime.now();
        List<Entity> newHerbivorePopulation = new GeneticAlgorithm(simulation.getHerbivoresGenotypes()).run().stream()
                .map(x -> EntityFactory.getEntity(EntityType.HERBIVORE, x)).collect(Collectors.toList());
        List<Entity> newCarnivorePopulation = new GeneticAlgorithm(simulation.getCarnivoresGenotypes()).run().stream()
                .map(x -> EntityFactory.getEntity(EntityType.CARNIVORE, x)).collect(Collectors.toList());
        LocalTime end = LocalTime.now();
        System.out.println("Finished GA: " + ((end.getNano() - start.getNano()) / 1000000) + " ms");
        this.simulation = new Simulation(newHerbivorePopulation, newCarnivorePopulation);
        System.out.println("Restarting");
        start();

        if(fastForwardOnFinish){
            System.out.println("fastForwardOnFinish");
            startFastForward();
        }
        i++;
    }

    @Override
    protected void onReset() {
        start();
    }

    public Simulation getSimulation() {
        return simulation;
    }
}
