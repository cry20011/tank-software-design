package ru.mipt.bit.platformer.game.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.Level;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.actions.Direction;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.game.game_engine.CollisionDetector.getCollidedObject;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Tank implements MapObject, Movable, Shootable, Damagable {
    private final float DEFAULT_MOVEMENT_SPEED = 0.4f;
    private final Direction DEFAULT_DIRECTION = Direction.UP;
    private static final float MOVEMENT_COMPLETED = 1f;
    private static final int MOVEMENT_STARTED = 0;
    private final long RELOAD_MILLIS = 500;
    private LocalTime lastShootTime = LocalTime.now();
    private final float movementSpeed;
    private float movementProgress;
    private GridPoint2 coordinates;
    private GridPoint2 destinationCoordinates;
    private Direction direction;
    private int health = 100;

    public Tank(GridPoint2 coordinates) {
        this.movementProgress = 1;
        this.coordinates = coordinates;
        this.destinationCoordinates = coordinates;
        this.direction = DEFAULT_DIRECTION;
        this.movementSpeed = DEFAULT_MOVEMENT_SPEED;
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

            if (getCollidedObject(this, targetCoordinates) == null) {
                moveTo(targetCoordinates);
            }

            rotate(direction);
        }
    }

    @Override
    public void shoot() {
        if (readyToShoot()) {
            Level.get().add(new Bullet(direction.apply(coordinates), direction));
            lastShootTime = LocalTime.now();
        }
    }

    private boolean readyToShoot() {
        return LocalTime.now().isAfter(lastShootTime.plus(RELOAD_MILLIS, ChronoUnit.MILLIS));
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public void receiveDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            Level.get().remove(this);
        }
    }

    @Override
    public int getHealth() {
        return this.health;
    }
}