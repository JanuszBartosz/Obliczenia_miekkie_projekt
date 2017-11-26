package pl.edu.pwr.engine.simulation;

import com.badlogic.gdx.graphics.Color;
import pl.edu.pwr.graphics.Entity;

public class Plant extends Entity {

    public Plant(float x, float y, Color color, float radius) {
        super(x, y, 0, 0, color, radius);
    }

    @Override
    public void setNextInputs(double[] inputs) {
    }
}
