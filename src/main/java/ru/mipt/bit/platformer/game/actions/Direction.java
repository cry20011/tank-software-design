package ru.mipt.bit.platformer.game.actions;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.entities.Movable;

import java.util.concurrent.ThreadLocalRandom;

public enum Direction implements Action {
    UP(new GridPoint2(0,1), 90),
    RIGHT(new GridPoint2(1,0), 0),
    LEFT(new GridPoint2(-1,0), -180),
    DOWN(new GridPoint2(0,-1), -90);

    private final GridPoint2 vector;
    private final float rotation;

    Direction(GridPoint2 vector, float rotation) {
        this.vector = vector;
        this.rotation = rotation;
    }

    public static Direction randomDirection()  {
        Direction[] directions = values();
        return directions[ThreadLocalRandom.current().nextInt(0, directions.length)];
    }

    public GridPoint2 apply(GridPoint2 point) {
        return point.cpy().add(vector);
    }

    @Override
    public void apply(GameObject object) {
        ((Movable) object).move(this);
    }

    public float getRotation() {
        return rotation;
    }
}