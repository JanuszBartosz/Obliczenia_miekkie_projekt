package pl.edu.pwr.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import pl.edu.pwr.engine.Parameters;
import pl.edu.pwr.engine.training.Genotype;

import java.util.*;

public class Entity {

    // ===== PRIVATE =====
    protected final static float maxAngle = 2 * (float) Math.PI;
    // Current entity position and speed
    protected float speed;
    // Facing angle of entity in radians, counterclockwise
    // 0 means facing right, pi facing left
    protected float angle;
    protected int fitness;
    private float x;
    private float y;
    // Appereance variables
    private Color color;
    private float radius;
    private ArrayList<RelativeEntity> children;

    // ===== PUBLIC =====
    public Entity(float x, float y, float speed, float angle, Color color, float radius) {
        setX(x);
        setY(y);
        setSpeed(speed);
        setAngle(angle);
        setColor(color);
        setRadius(radius);
        children = new ArrayList<>();
    }

    static void setBorders(float x, float y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException(String.format("Arguments must be positive values: x=%f, y=%f", x, y));
        }

        Parameters.borderX = x;
        Parameters.borderY = y;
        Parameters.maxDistance = Math.sqrt(Parameters.borderX * Parameters.borderX + Parameters.borderY * Parameters.borderY);
    }

    public static void draw(ShapeRenderer shapeRenderer, ArrayList<Entity> entities) {
        for (Entity e : entities) {
            e.draw(shapeRenderer);
        }
    }

    public static Map<Entity, Set<Entity>> getIntersectedEntities(ArrayList<Entity> entities) {
        Map<Entity, Set<Entity>> retVal = new HashMap<>();
        if (entities != null) {
            for (int i = 0; i < entities.size(); i++) {
                for (int j = i + 1; j < entities.size(); j++) {
                    final float radiusSum = entities.get(i).getRadius() + entities.get(j).getRadius();
                    final float e1X = entities.get(i).getX();
                    final float e1Y = entities.get(i).getY();
                    final float e2X = entities.get(j).getX();
                    final float e2Y = entities.get(j).getY();
                    final float distanceNormal = euclideanDistance(e1X, e1Y, e2X, e2Y);
                    final float distanceTransposed = euclideanDistance(e1X,
                            e1Y,
                            e2X - Parameters.borderX,
                            e2Y - Parameters.borderY);
                    // Get minimal value, because map is a torus
                    final float distance = Math.min(distanceNormal, distanceTransposed);

                    // Detect intersection and add both entities to list
                    if (distance < radiusSum) {
                        // Add to first's entity set
                        Set<Entity> firstSet = retVal.get(entities.get(i));
                        if (firstSet == null) {
                            firstSet = new HashSet<>();
                        }
                        firstSet.add(entities.get(j));
                        retVal.put(entities.get(i), firstSet);

                        // Add to second's entity set
                        Set<Entity> secondSet = retVal.get(entities.get(j));
                        if (secondSet == null) {
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

    public static Map<Entity, Set<Entity>> getIntersectedEntities(List<? extends Entity> intersectors,
                                                                  List<? extends Entity> intersectees) {
        Map<Entity, Set<Entity>> retVal = new HashMap<>();
        if (intersectors != null && intersectees != null) {
            for (Entity intersector : intersectors) {
                for (Entity intersectee : intersectees) {
                    final float radiusSum = intersector.getRadius() + intersectee.getRadius();
                    final float e1X = intersector.getX();
                    final float e1Y = intersector.getY();
                    final float e2X = intersectee.getX();
                    final float e2Y = intersectee.getY();

                    final float distance = getDistanceOnTorus(e1X, e1Y, e2X, e2Y);

                    // Detect intersection and add entry only to intersectors
                    if (distance < radiusSum) {
                        Set<Entity> set = retVal.get(intersector);
                        if (set == null) {
                            set = new HashSet<>();
                        }
                        set.add(intersectee);
                        retVal.put(intersector, set);
                    }
                }
            }
        }

        return retVal;
    }

    public static float euclideanDistance(float x1, float y1, float x2, float y2) {
        final float xDiff = Math.abs(x1 - x2);
        final float yDiff = Math.abs(y1 - y2);
        return (float) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    public static float getDistanceOnTorus(float x1, float y1, float x2, float y2) {
        final float distanceNormal = euclideanDistance(x1, y1, x2, y2);
        final float distanceTransposed = euclideanDistance(x1, y1, x2 - Parameters.borderX, y2 - Parameters.borderY);
        // Get minimal value, because map is a torus
        return Math.min(distanceNormal, distanceTransposed);
    }

    // Adds child that is drawn relative to parent
    public <T extends Entity> RelativeEntity addRelativeChild(T entity, float radius, float angle) {
        return addRelativeChild(new RelativeEntity(entity, radius, angle));
    }

    private RelativeEntity addRelativeChild(RelativeEntity entity) {
        children.add(entity);
        return entity;
    }

    public void calculateChildrenPositions() {
        for (RelativeEntity re : children) {
            re.setAngle(getAngle() + re.getRelativeAngle());
            if (re.getRelativeRadius() != 0) {
                re.setX(getX() + (float) Math.sin(re.getAngle()) * re.getRelativeRadius());
                re.setY(getY() + (float) Math.cos(re.getAngle()) * re.getRelativeRadius());
            }
        }
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
        drawChildren(shapeRenderer);
    }

    private void drawChildren(ShapeRenderer shapeRenderer) {
        for (RelativeEntity re : children) {
            re.draw(shapeRenderer);
        }
    }

    public void fitnessPenalty(){
        fitness -= 4000;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        // Ensure angle is in maxAngle range
        angle %= maxAngle;

        if (angle < 0) {
            angle = maxAngle + angle;
        }

        this.angle = angle;
    }

    public ArrayList<RelativeEntity> getChildren() {
        return children;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getFitness(){
        return fitness;
    }

    public int getFullness() {
        return 0;
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

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
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

    public void incrementFoundFood(int size) {
        fitness += size;
    }

    public boolean isAlive(){
        return true;
    }

    public boolean makeStep() {
        if (speed != 0) {
            setX(getX() + (float) Math.sin(angle) * speed);
            setY(getY() + (float) Math.cos(angle) * speed);
            calculateChildrenPositions();
        }
        return false;
    }

    public Genotype mapToGenotype() {
        return null;
    }

    public void resetFullness() {
    }

    public void setNextInputs(double[] inputs) {
    }

    @Override
    public String toString() {
        return String.format("x=%f, y=%f, speed=%f, angle=%f, radius=%f", x, y, speed, angle, radius);
    }
}
