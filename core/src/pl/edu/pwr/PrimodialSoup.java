package pl.edu.pwr;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PrimodialSoup extends ApplicationAdapter {
	public PrimodialSoup(int width, int height){
		Entity.setBorders(width, height);
	}

	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private ArrayList<Entity> entities;

	private final static long millisecondsPerTick = 10;
	private final static long millisecondsToChangeAngle = 1000;
	private Timer stepTimer;
	private Timer angleTimer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		entities = new ArrayList<Entity>();
		entities.add(new Entity(200,200,1, 0, Color.BLACK, 10));
		entities.add(new Entity(200,200,1, (float)Math.PI / 2, Color.RED, 10));
		entities.add(new Entity(200,200,1, (float)Math.PI, Color.GREEN, 10));
		entities.add(new Entity(200,200,1, (float)Math.PI * 3 / 2, Color.BLUE, 10));

		stepTimer = new Timer("Step make timer");
		stepTimer.scheduleAtFixedRate(new EntityStepTask(entities), 0, millisecondsPerTick);

		angleTimer = new Timer("Angle change timer");
		angleTimer.scheduleAtFixedRate(new EntityAngleTask(entities), 0, millisecondsToChangeAngle);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		Entity.draw(shapeRenderer, entities);
		shapeRenderer.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		stepTimer.cancel();
		angleTimer.cancel();
	}
}
