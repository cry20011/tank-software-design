package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.instructions.Direction;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements Movable {
    public static final float MOVEMENT_COMPLETED = 1f;
    public static final int MOVEMENT_STARTED = 0;
    private final float movementSpeed;
    private float movementProgress;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private Direction direction;

    public Tank(GridPoint2 coordinates, Direction direction, float movementSpeed) {
        this.movementProgress = 1;
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.direction = direction;
        this.movementSpeed = movementSpeed;
    }

    public boolean isMoving() {
        return isEqual(movementProgress, MOVEMENT_COMPLETED);
    }

    public void moveTo(GridPoint2 tankTargetCoordinates) {
        destinationCoordinates = tankTargetCoordinates;
        movementProgress = MOVEMENT_STARTED;
    }

    public void rotate(Direction direction) {
        this.direction = direction;
    }

    public void moveIfNotCollides(Direction direction, List<MapObject> obstacles) {
        if (isMoving()) {
            GridPoint2 targetCoordinates = direction.apply(coordinates);

            if (!collides(targetCoordinates, obstacles)) {
                moveTo(targetCoordinates);
            }

            rotate(direction);
        }
    }

    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isMoving()) {
            coordinates = destinationCoordinates;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean collides(GridPoint2 targetCoordinates, List<MapObject> others) {
        for (MapObject other: others) {
            if (other != this) {
                if (targetCoordinates.equals(other.getCoordinates())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public float getRotation() {
        return getDirection().getRotation();
    }

    @Override
    public void apply(Instruction instruction, List<MapObject> objects) {
        if (instruction instanceof Direction) {
            moveIfNotCollides((Direction) instruction, objects);
        }
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }
}