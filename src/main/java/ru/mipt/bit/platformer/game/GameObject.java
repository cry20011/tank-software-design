package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.actions.Direction;

public interface GameObject {
    GridPoint2 getCoordinates();
    default Direction getDirection() {return Direction.UP;}
    default void updateState(float deltaTime) {}
    default GridPoint2 getDestinationCoordinates() {return getCoordinates();}
    default float getMovementProgress() {return 1f;}
}
