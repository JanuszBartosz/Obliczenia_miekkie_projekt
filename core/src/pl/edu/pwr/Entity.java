package pl.edu.pwr;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public class Entity {
    // ===== PUBLIC =====
    Entity(float x, float y, float speed, float angle, Color color, float radius){
        setX(x);
        setY(y);
        setSpeed(speed);
        setAngle(angle);
        setColor(color);
        setRadius(radius);
    }

    public void setX(float x){
        // Ensure x is in borders range
        x %= borderX;

        if(x < 0){
            x = borderX + x;
        }

        this.x = x;
    }

    public float getX(){
        return x;
    }

    public void setY(float y){
        // Ensure y is in borders range
        y %= borderY;

        if(y < 0){
            y = borderY + y;
        }

        this.y = y;
    }

    public float getY(){
        return y;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public float getSpeed(){
        return speed;
    }

    public void setAngle(float angle){
        // Ensure angle is in maxAngle range
        angle %= maxAngle;

        if(angle < 0){
            angle = maxAngle - angle;
        }

        this.angle = angle;
    }

    public float getAngle(){
        return angle;
    }

    public static void setBorders(float x, float y) {
        if(x < 0 || y < 0)
        {
            throw new IllegalArgumentException(String.format("Arguments must be positive values: x=%f, y=%f", x, y));
        }

        borderX = x;
        borderY = y;
    }

    public void makeStep(){
        if(speed != 0){
            setX(getX() + (float)Math.sin(angle) * speed);
            setY(getY() + (float)Math.cos(angle) * speed);
        }
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public void setRadius(float radius) {
        if(radius < 0) {
            throw new IllegalArgumentException("Radius ust be positive");
        }
        this.radius = radius;
    }

    public float getRadius(){
        return radius;
    }

    public void draw(ShapeRenderer shapeRenderer){
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, radius);
    }

    public static void draw(ShapeRenderer shapeRenderer, ArrayList<Entity> entities){
        for(Entity e : entities){
            e.draw(shapeRenderer);
        }
    }

    @Override
    public String toString() {
        return String.format("x=%f, y=%f, speed=%f, angle=%f, radius=%f", x, y, speed, angle, radius);
    }

    // ===== PRIVATE =====
    // Current entity position and speed
    private float x;
    private float y;
    private float speed;

    // Facing angle of entity in radians, counterclockwise
    // 0 means facing right, pi facing left
    private float angle;
    private final static float maxAngle = 2 * (float)Math.PI;

    // Board borders
    private static float borderX;
    private static float borderY;

    // Appereance variables
    private Color color;
    private float radius;
}
