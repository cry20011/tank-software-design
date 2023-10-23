package ru.mipt.bit.platformer.instructions;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entities.MapObject;

import java.util.concurrent.ThreadLocalRandom;

public enum Direction implements Instruction {
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
    public void apply(MapObject object) {
        object.move(this);
    }

    public float getRotation() {
        return rotation;
    }
}