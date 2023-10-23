package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.controllers.CollisionDetector;
import ru.mipt.bit.platformer.instructions.Direction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TankTest {
    @Test
    public void TestTank_moveNoCollisions() {
        float MOVEMENT_SPEED = 1f;
        float MOVEMENT_STARTED = 0f;
        float MOVEMENT_COMPLETED = 1f;


        GridPoint2 currCoordinates = new GridPoint2(1, 1);

        Tank tank = new Tank(currCoordinates, Direction.RIGHT, MOVEMENT_SPEED);

        tank.move(Direction.RIGHT);

        assertEquals(currCoordinates, tank.getCoordinates());
        assertEquals(Direction.RIGHT.apply(currCoordinates), tank.getDestinationCoordinates());
        assertEquals(MOVEMENT_STARTED, tank.getMovementProgress());

        tank.updateState(1f);

        currCoordinates = Direction.RIGHT.apply(currCoordinates);

        assertEquals(currCoordinates, tank.getCoordinates());
        assertEquals(MOVEMENT_COMPLETED, tank.getMovementProgress());

        tank.move(Direction.LEFT);

        assertEquals(currCoordinates, tank.getCoordinates());
        assertEquals(Direction.LEFT.apply(currCoordinates), tank.getDestinationCoordinates());
        assertEquals(MOVEMENT_STARTED, tank.getMovementProgress());
        assertEquals(Direction.LEFT.getRotation(), tank.getRotation());
    }

    @Test
    public void TestTank_tryMoveOnCollision() {
        float MOVEMENT_SPEED = 1f;
        float MOVEMENT_STARTED = 0f;
        float MOVEMENT_COMPLETED = 1f;
        CollisionDetector collisionDetector = new CollisionDetector(2, 2);
        collisionDetector.add(new Tree(new GridPoint2(1, 2)));


        GridPoint2 currCoordinates = new GridPoint2(1, 1);

        Tank tank = new Tank(currCoordinates, Direction.RIGHT, MOVEMENT_SPEED);

        tank.move(Direction.UP);

        assertEquals(currCoordinates, tank.getCoordinates());
        assertNotEquals(Direction.RIGHT.apply(currCoordinates), tank.getDestinationCoordinates());
        assertNotEquals(MOVEMENT_STARTED, tank.getMovementProgress());

        tank.updateState(1f);

        assertEquals(currCoordinates, tank.getCoordinates());
        assertEquals(MOVEMENT_COMPLETED, tank.getMovementProgress());
        assertEquals(Direction.UP.getRotation(), tank.getRotation());
    }
}