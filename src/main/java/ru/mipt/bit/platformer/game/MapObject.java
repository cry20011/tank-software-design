package ru.mipt.bit.platformer.game;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.actions.Direction;

public interface MapObject {
    GridPoint2 getCoordinates();
    default float getRotation() {return 0f;}
    default void updateState(float deltaTime) {}
    default GridPoint2 getDestinationCoordinates() {return getCoordinates();}
    default float getMovementProgress() {return 1f;}
    default void move(Direction direction) {}

    default void applyNextAction() {}
}
