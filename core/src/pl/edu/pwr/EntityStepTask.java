package pl.edu.pwr;

import pl.edu.pwr.graphics.Entity;

import java.util.*;

public class EntityStepTask extends TimerTask{
    public EntityStepTask(ArrayList<Entity> entities){
        this.entities = entities;
    }

    @Override
    public void run() {
        for(Entity e : entities){
            e.makeStep();
        }

        Map<Entity, Set<Entity>> intersections = Entity.getIntersectedEntities(entities);
        Iterator it = intersections.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println("Entity: <" + pair.getKey().toString() + "> intersect with: ");

            Set<Entity> set = (Set<Entity>)pair.getValue();
            for(Entity e : set){
                System.out.println(" - {" + e.toString() + "}");
            }

            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    private ArrayList<Entity> entities;
}
