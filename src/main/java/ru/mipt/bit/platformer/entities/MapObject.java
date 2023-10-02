package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.util.List;

public interface MapObject {
    GridPoint2 getCoordinates();
    default float getRotation() {
        return 0f;
    }

    void apply(Instruction key, List<MapObject> objects);
}
