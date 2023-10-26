package ru.mipt.bit.platformer.game.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.Level;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.actions.Direction;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.game.game_engine.CollisionDetector.collides;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet implements MapObject, Movable {
    private final float DEFAULT_MOVEMENT_SPEED = 0.1f;
    public static final float MOVEMENT_COMPLETED = 1f;
    public static final int MOVEMENT_STARTED = 0;
    private final float movementSpeed;
    private float movementProgress;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private final Direction direction;


    public Bullet(GridPoint2 coordinates, Direction direction) {
        this.movementProgress = 1;
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.direction = direction;
        this.movementSpeed = DEFAULT_MOVEMENT_SPEED;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isMoving()) {
            coordinates = destinationCoordinates;
        }
    }
    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    private boolean isMoving() {
        return isEqual(movementProgress, MOVEMENT_COMPLETED);
    }

    private void moveTo(GridPoint2 tankTargetCoordinates) {
        destinationCoordinates = tankTargetCoordinates;
        movementProgress = MOVEMENT_STARTED;
    }

    @Override
    public void move(Direction direction) {
        if (isMoving()) {
            GridPoint2 targetCoordinates = direction.apply(coordinates);

            if (!collides(this, targetCoordinates)) {
                moveTo(targetCoordinates);
            } else {
                Level.get().remove(this);
            }
        }
    }
}
