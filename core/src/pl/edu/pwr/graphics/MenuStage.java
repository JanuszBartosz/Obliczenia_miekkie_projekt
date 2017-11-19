package pl.edu.pwr.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MenuStage extends Stage {
    public MenuStage(int width, int height, int midpointX){
        this.width = width;
        this.height = height;
        this.midpointX = midpointX;
        shapeRenderer = new ShapeRenderer();
    }

    private int width;
    private int height;
    private int midpointX;
    private ShapeRenderer shapeRenderer;

    @Override
    public void draw() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.rect(midpointX, 0, width, height);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
