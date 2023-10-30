package ru.mipt.bit.platformer.game;

import java.util.Map;

public interface ObjectsController extends LevelListener {
    Map<MapObject, Action> nextActions();
}
