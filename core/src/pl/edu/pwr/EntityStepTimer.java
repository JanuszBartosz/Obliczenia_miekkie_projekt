package pl.edu.pwr;

import pl.edu.pwr.engine.simulation.Simulation;
import pl.edu.pwr.graphics.Entity;

import java.util.*;

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
        // Nothing to do
    }

    @Override
    protected void onReset() {
        simulation = new Simulation();
    }

    public Simulation getSimulation() {
        return simulation;
    }
}
