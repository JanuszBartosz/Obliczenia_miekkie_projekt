package pl.edu.pwr.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.edu.pwr.EntityStepTimer;
import pl.edu.pwr.ForwardableTimer;
import pl.edu.pwr.Timer;
import pl.edu.pwr.engine.simulation.Simulation;

import java.util.ArrayList;

public class SoupStage extends Stage {

    private ShapeRenderer shapeRenderer;
    private ArrayList<Entity> entities;

    private final static long millisecondsPerTick = 10;
    private final static long millisecondsToChangeAngle = 1000;
    private ForwardableTimer stepTimer;
    private Simulation simulation;

    public SoupStage(int width, int height) {
        Entity.setBorders(width, height);

        shapeRenderer = new ShapeRenderer();
        this.simulation = new Simulation();
        stepTimer = new EntityStepTimer(simulation, millisecondsPerTick, Timer.DURATION_INFINITY);
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        simulation.drawAll(shapeRenderer);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        stepTimer.cancel();
    }

    public ForwardableTimer getStepTimer() {
        return stepTimer;
    }
}
