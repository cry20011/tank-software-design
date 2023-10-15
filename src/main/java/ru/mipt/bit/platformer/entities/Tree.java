package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.math.GridPoint2;

public class Tree implements MapObject {
    private final GridPoint2 coordinates;

    public Tree(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }
}
