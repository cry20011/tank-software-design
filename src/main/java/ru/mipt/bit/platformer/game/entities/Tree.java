package ru.mipt.bit.platformer.game.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.ActionGenerator;

public class Tree implements MapObject {
    private final GridPoint2 coordinates;
    private final ActionGenerator actionGenerator;

    public Tree(GridPoint2 coordinates) {
        this.coordinates = coordinates;
        this.actionGenerator = null;
    }

    public Tree(GridPoint2 coordinates, ActionGenerator actionGenerator) {
        this.coordinates = coordinates;
        this.actionGenerator = actionGenerator;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return coordinates;
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
}
