package pl.edu.pwr.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

public class Entity {
    // ===== PUBLIC =====
    public Entity(float x, float y, float speed, float angle, Color color, float radius){
        setX(x);
        setY(y);
        setSpeed(speed);
        setAngle(angle);
        setColor(color);
        setRadius(radius);
        children = new ArrayList<>();
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
            calculateChildrenPositions();
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

    private void draw(ShapeRenderer shapeRenderer, float x, float y){
        shapeRenderer.setColor(color);
        shapeRenderer.circle(x, y, radius);
    }

    public void draw(ShapeRenderer shapeRenderer){
        draw(shapeRenderer, x, y);

        // Draw entity on different side
        float newX = x;
        float newY = y;
        if(x < radius){
            newX = x + borderX;
        }

        if(y < radius){
            newY = y + borderY;
        }

        if(x + radius > borderX){
            newX = x - borderX;
        }

        if(y + radius > borderY){
            newY = y - borderY;
        }

        draw(shapeRenderer, newX, newY);
        drawChildren(shapeRenderer);
    }

    private void calculateChildrenPositions() {
        for(RelativeEntity re : children){
            re.setAngle(getAngle() + re.getRelativeAngle());
            if(re.getRelativeRadius() != 0){
                re.setX(getX() + (float)Math.sin(re.getAngle()) * re.getRelativeRadius());
                re.setY(getY() + (float)Math.cos(re.getAngle()) * re.getRelativeRadius());
            }
        }
    }

    private void drawChildren(ShapeRenderer shapeRenderer) {
        for(RelativeEntity re : children){
            re.draw(shapeRenderer);
        }
    }

    public static void draw(ShapeRenderer shapeRenderer, ArrayList<Entity> entities){
        for(Entity e : entities){
            e.draw(shapeRenderer);
        }
    }

    // Adds child that is drawn relative to parent
    public void addRelativeChild(Entity entity, float radius, float angle){
        addRelativeChild(new RelativeEntity(entity, radius, angle));
    }

    public void addRelativeChild(RelativeEntity entity){
        children.add(entity);
    }

    @Override
    public String toString() {
        return String.format("x=%f, y=%f, speed=%f, angle=%f, radius=%f", x, y, speed, angle, radius);
    }

    public static Map<Entity, Set<Entity>> getIntersectedEntities(ArrayList<Entity> entities){

        Map<Entity, Set<Entity>> retVal = new HashMap<>();

        if(entities != null){
            for(int i = 0; i < entities.size(); i++){
                for(int j = i + 1; j < entities.size(); j++){
                    final float radiusSum = entities.get(i).getRadius() + entities.get(j).getRadius();
                    final float e1X = entities.get(i).getX();
                    final float e1Y = entities.get(i).getY();
                    final float e2X = entities.get(j).getX();
                    final float e2Y = entities.get(j).getY();

                    final float distanceNormal = euclideanDistance(e1X, e1Y, e2X, e2Y);
                    final float distanceTransposed = euclideanDistance(e1X, e1Y, e2X - borderX, e2Y - borderY);
                    // Get minimal value, because map is a torus
                    final float distance = Math.min(distanceNormal, distanceTransposed);

                    // Detect intersection and add both entities to list
                    if(distance < radiusSum){
                        // Add to first's entity set
                        Set<Entity> firstSet = retVal.get(entities.get(i));
                        if(firstSet == null){
                            firstSet = new HashSet<>();
                        }
                        firstSet.add(entities.get(j));
                        retVal.put(entities.get(i), firstSet);

                        // Add to second's entity set
                        Set<Entity> secondSet = retVal.get(entities.get(j));
                        if(secondSet == null){
                            secondSet = new HashSet<>();
                        }
                        secondSet.add(entities.get(i));
                        retVal.put(entities.get(j), secondSet);
                    }
                }
            }
        }

        return retVal;
    }

    static float euclideanDistance(float x1, float y1, float x2, float y2){
        final float xDiff = Math.abs(x1 - x2);
        final float yDiff = Math.abs(y1 - y2);
        return (float)Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    protected final static float maxAngle = 2 * (float)Math.PI;

    // ===== PRIVATE =====
    // Current entity position and speed
    private float x;
    private float y;
    private float speed;

    // Facing angle of entity in radians, counterclockwise
    // 0 means facing right, pi facing left
    private float angle;

    // Board borders
    private static float borderX;
    private static float borderY;

    // Appearance variables
    private Color color;
    private float radius;

    private ArrayList<RelativeEntity> children;
}
