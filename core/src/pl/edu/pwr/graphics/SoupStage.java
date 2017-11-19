package pl.edu.pwr.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.edu.pwr.EntityAngleTask;
import pl.edu.pwr.EntityStepTask;

import java.util.ArrayList;
import java.util.Timer;

public class SoupStage extends Stage {
    private ShapeRenderer shapeRenderer;
    private ArrayList<Entity> entities;

    private final static long millisecondsPerTick = 10;
    private final static long millisecondsToChangeAngle = 1000;
    private Timer stepTimer;
    private Timer angleTimer;

    public SoupStage(int width, int height) {
        Entity.setBorders(width, height);

        shapeRenderer = new ShapeRenderer();

        entities = new ArrayList<>();
        entities.add(new Entity(200,200,1, 0, Color.BLACK, 10));
        entities.add(new Entity(200,200,1, (float)Math.PI / 2, Color.RED, 10));
        entities.add(new Entity(200,200,1, (float)Math.PI, Color.GREEN, 10));
        entities.add(new Entity(200,200,1, (float)Math.PI * 3 / 2, Color.BLUE, 10));

        stepTimer = new Timer("Step make timer");
        stepTimer.scheduleAtFixedRate(new EntityStepTask(entities), 0, millisecondsPerTick);

        angleTimer = new Timer("Angle change timer");
        angleTimer.scheduleAtFixedRate(new EntityAngleTask(entities), millisecondsToChangeAngle, millisecondsToChangeAngle);
    };


    @Override
    public void draw() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Entity.draw(shapeRenderer, entities);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        stepTimer.cancel();
        angleTimer.cancel();
    }
}
