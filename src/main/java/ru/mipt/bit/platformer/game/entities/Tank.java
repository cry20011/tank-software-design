package ru.mipt.bit.platformer.game.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.actions.Direction;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.ActionGenerator;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.game.game_engine.CollisionDetector.collides;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements MapObject {
    private final float DEFAULT_MOVEMENT_SPEED = 0.4f;
    private final Direction DEFAULT_DIRECTION = Direction.UP;
    public static final float MOVEMENT_COMPLETED = 1f;
    public static final int MOVEMENT_STARTED = 0;
    private final float movementSpeed;
    private float movementProgress;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private Direction direction;

    private final ActionGenerator actionGenerator;

    public Tank(GridPoint2 coordinates, ActionGenerator actionGenerator) {
        this.movementProgress = 1;
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.direction = DEFAULT_DIRECTION;
        this.movementSpeed = DEFAULT_MOVEMENT_SPEED;
        this.actionGenerator = actionGenerator;
    }

    private boolean isMoving() {
        return isEqual(movementProgress, MOVEMENT_COMPLETED);
    }

    private void moveTo(GridPoint2 tankTargetCoordinates) {
        destinationCoordinates = tankTargetCoordinates;
        movementProgress = MOVEMENT_STARTED;
    }

    private void rotate(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void updateState(float deltaTime) {
        movementProgress = continueProgress(movementProgress, deltaTime, movementSpeed);
        if (isMoving()) {
            coordinates = destinationCoordinates;
        }
    }

    @Override
    public void move(Direction direction) {
        if (isMoving()) {
            GridPoint2 targetCoordinates = direction.apply(coordinates);

            if (!collides(this, targetCoordinates)) {
                moveTo(targetCoordinates);
            }

            rotate(direction);
        }
    }

    @Override
    public void applyNextAction() {
        if (actionGenerator == null) {
            return;
        }

        Action action = actionGenerator.generateFor(this);
        if (action != null) {
            action.applyTo(this);
        }
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
        return direction.getRotation();
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }
}