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
//        Map<Entity, Set<Entity>> intersections = Entity.getIntersectedEntities(entities);
//        Iterator it = intersections.entrySet().iterator();
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry)it.next();
//            System.out.println("Entity: <" + pair.getKey().toString() + "> intersect with: ");
//
//            Set<Entity> set = (Set<Entity>)pair.getValue();
//            for(Entity e : set){
//                System.out.println(" - {" + e.toString() + "}");
//            }
//
//            it.remove(); // avoids a ConcurrentModificationException
//        }
    }

    @Override
    protected void onFinish() {

    }
}
