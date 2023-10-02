package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.util.List;

public class Tree implements MapObject {
    private final GridPoint2 coordinates;

    public Tree(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    @Override
    public void apply(Instruction key, List<MapObject> objects) {}
}
