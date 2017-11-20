package pl.edu.pwr;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import pl.edu.pwr.graphics.MenuStage;
import pl.edu.pwr.graphics.SoupStage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PrimordialSoup extends ApplicationAdapter {
	public PrimordialSoup(int width, int height){
		this.width = width;
		this.height = height;
	}

	private final static int sideBarWidth = 200;

	private int width;
	private int height;
	private Stage soupStage;
	private Stage menuStage;

	@Override
	public void create () {
		soupStage = new SoupStage(width - sideBarWidth, height);
		menuStage = new MenuStage(sideBarWidth, height, width - sideBarWidth);
	}

	@Override
	public void render () {
		soupStage.draw();
		menuStage.draw();
	}
	
	@Override
	public void dispose () {
		soupStage.dispose();
		menuStage.dispose();
	}
}