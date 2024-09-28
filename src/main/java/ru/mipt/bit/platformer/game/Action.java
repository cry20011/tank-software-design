package ru.mipt.bit.platformer.game;

public interface Action {
    void applyTo(MapObject object);
}
