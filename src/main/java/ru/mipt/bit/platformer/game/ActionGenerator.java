package ru.mipt.bit.platformer.game;

public interface ActionGenerator {
    Action generateFor(MapObject object);
}
