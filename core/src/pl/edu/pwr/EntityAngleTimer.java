package pl.edu.pwr;

import pl.edu.pwr.graphics.Entity;

import java.util.ArrayList;
import java.util.Random;

public class EntityAngleTimer extends ForwardableTimer{
    public EntityAngleTimer(ArrayList<Entity> entities, long interval, long duration){
        super(interval, duration);
        this.entities = entities;
    }

    @Override
    protected void onTick(){
        for(Entity e : entities){
            e.setAngle(random.nextFloat() * (float)Math.PI * 2);
        }
    }

    @Override
    protected void onFinish(){

    }

    private final Random random = new Random();
    private ArrayList<Entity> entities;
}
