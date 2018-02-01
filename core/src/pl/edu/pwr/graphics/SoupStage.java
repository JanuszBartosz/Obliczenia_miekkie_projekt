package pl.edu.pwr.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.edu.pwr.Dumper;
import pl.edu.pwr.EntityStepTimer;
import pl.edu.pwr.engine.Parameters;
import pl.edu.pwr.engine.simulation.Simulation;

public class SoupStage extends Stage {

    private ShapeRenderer shapeRenderer;


    // Simulation: 6000 ticks
    private final static long millisecondsPerTick = 20;
    private EntityStepTimer stepTimer;

    public SoupStage(int width, int height) {
        Entity.setBorders(width, height);

        shapeRenderer = new ShapeRenderer();
        stepTimer = new EntityStepTimer(new Simulation(), Parameters.tickInterval, Parameters.simulationTicks);
        if (Parameters.score) {
            Dumper.dumpParameters();
        }
    }

    @Override
    public void draw() {
        if (!stepTimer.isFastForwarding()) {
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            Simulation simulation = stepTimer.getSimulation();
            if (simulation != null) {
                simulation.drawAll(shapeRenderer);
            }

            shapeRenderer.end();
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        stepTimer.cancel();
    }

    public EntityStepTimer getStepTimer() {
        return stepTimer;
    }
}
