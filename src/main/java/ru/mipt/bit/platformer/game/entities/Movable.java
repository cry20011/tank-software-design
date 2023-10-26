package ru.mipt.bit.platformer.game.entities;

import ru.mipt.bit.platformer.game.actions.Direction;

public interface Movable {
    void move(Direction direction);
}
