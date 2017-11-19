package pl.edu.pwr;

import pl.edu.pwr.graphics.Entity;

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;

public class EntityAngleTask extends TimerTask {
    public EntityAngleTask(ArrayList<Entity> entities){
        this.entities = entities;
    }

    @Override
    public void run() {
        for(Entity e : entities){
            e.setAngle(random.nextFloat() * (float)Math.PI * 2);
        }
    }

    private final Random random = new Random();
    private ArrayList<Entity> entities;
}
