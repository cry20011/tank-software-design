package ru.mipt.bit.platformer.game.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.GameObject;

public class Tree implements GameObject {
    private final GridPoint2 coordinates;

    public Tree(GridPoint2 coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
    }
}
