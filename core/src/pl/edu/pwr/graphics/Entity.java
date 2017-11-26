package pl.edu.pwr.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import pl.edu.pwr.engine.Parameters;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Entity {

    // ===== PRIVATE =====
    protected final static float maxAngle = 2 * (float) Math.PI;
    protected final static float maxSpeed = 2;
    // Current entity position and speed
    protected float speed;
    // Facing angle of entity in radians, counterclockwise
    // 0 means facing right, pi facing left
    protected float angle;
    private float x;
    private float y;
    // Appereance variables
    private Color color;
    private float radius;

    // ===== PUBLIC =====
    public Entity(float x, float y, float speed, float angle, Color color, float radius) {
        setX(x);
        setY(y);
        setSpeed(speed);
        setAngle(angle);
        setColor(color);
        setRadius(radius);
    }

    public static void setBorders(float x, float y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException(String.format("Arguments must be positive values: x=%f, y=%f", x, y));
        }

        Parameters.borderX = x;
        Parameters.borderY = y;
    }

    public static void draw(ShapeRenderer shapeRenderer, ArrayList<Entity> entities) {
        for (Entity e : entities) {
            e.draw(shapeRenderer);
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        // Ensure x is in borders range
        x %= Parameters.borderX;

        if (x < 0) {
            x = Parameters.borderX + x;
        }

        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        // Ensure y is in borders range
        y %= Parameters.borderY;

        if (y < 0) {
            y = Parameters.borderY + y;
        }

        this.y = y;
    }

    public void setNextInputs(double[] inputs) {
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        // Ensure angle is in maxAngle range
        angle %= maxAngle;

        if (angle < 0) {
            angle = maxAngle - angle;
        }

        this.angle = angle;
    }

    public void makeStep() {
        if (speed != 0) {
            setX(getX() + (float) Math.sin(angle) * speed);
            setY(getY() + (float) Math.cos(angle) * speed);
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius ust be positive");
        }
        this.radius = radius;
    }

    private void draw(ShapeRenderer shapeRenderer, float x, float y) {
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, radius);
    }

    public void draw(ShapeRenderer shapeRenderer) {
        draw(shapeRenderer, x, y);

        // Draw entity on different side
        float newX = x;
        float newY = y;
        if (x < radius) {
            newX = x + Parameters.borderX;
        }

        if (y < radius) {
            newY = y + Parameters.borderY;
        }

        if (x + radius > Parameters.borderX) {
            newX = x - Parameters.borderX;
        }

        if (y + radius > Parameters.borderY) {
            newY = y - Parameters.borderY;
        }

        draw(shapeRenderer, newX, newY);
    }

    @Override
    public String toString() {
        return String.format("x=%f, y=%f, speed=%f, angle=%f, radius=%f", x, y, speed, angle, radius);
    }
}
