package pl.edu.pwr.graphics;

public class RelativeEntity extends Entity {
    public RelativeEntity(Entity entity, float radius, float angle){
        super(entity.getX(),
                entity.getY(),
                entity.getSpeed(),
                entity.getAngle(),
                entity.getColor(),
                entity.getRadius());
        setRelativeRadius(radius);
        setRelativeAngle(angle);
    }
    public void setRelativeRadius(float relativeRadius) {
        if(relativeRadius < 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.relativeRadius = relativeRadius;
    }

    public float getRelativeRadius(){
        return relativeRadius;
    }

    public void setRelativeAngle(float relativeAngle){
        // Ensure angle is in maxAngle range
        relativeAngle %= maxAngle;

        if(relativeAngle < 0){
            relativeAngle = maxAngle + relativeAngle;
        }

        this.relativeAngle = relativeAngle;
    }

    public float getRelativeAngle(){
        return relativeAngle;
    }

    private float relativeRadius;
    private float relativeAngle;
}
