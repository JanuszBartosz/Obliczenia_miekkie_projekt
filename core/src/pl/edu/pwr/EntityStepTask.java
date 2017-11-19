package pl.edu.pwr;

import pl.edu.pwr.graphics.Entity;

import java.util.ArrayList;
import java.util.TimerTask;

public class EntityStepTask extends TimerTask{
    public EntityStepTask(ArrayList<Entity> entities){
        this.entities = entities;
    }

    @Override
    public void run() {
        for(Entity e : entities){
            e.makeStep();
        }
    }

    private ArrayList<Entity> entities;
}
