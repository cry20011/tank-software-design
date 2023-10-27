package ru.mipt.bit.platformer.game;

import java.util.*;

public class Level {
    private static Level level = null;
    public static Level get() {
        return level;
    }

    private final List<ObjectsController> objectControllers = new ArrayList<>();
    private final List<LevelListener> levelListeners = new ArrayList<>();

    private final MapObject player;
    private final List<MapObject> objects = new ArrayList<>();

    public Level(MapObject player, List<ObjectsController> objectControllers, List<LevelListener> levelListeners) {
        level = this;

        this.levelListeners.addAll(levelListeners);
        this.objectControllers.addAll(objectControllers);
        this.player = player;

        levelListeners.forEach(handler -> handler.add(player));
    }

    public void add(MapObject object) {
        objectControllers.forEach(controller -> controller.add(object));
        levelListeners.forEach(handler -> handler.add(object));
        objects.add(object);
    }

    public void remove(MapObject object) {
        objectControllers.forEach(controller -> controller.remove(object));
        levelListeners.forEach(listener -> listener.remove(object));
        objects.remove(object);
    }

    public void applyActions() {
        objectControllers.forEach(controller -> controller.nextActions().forEach((object, action) -> action.applyTo(object)));
    }

    public void updateState(float deltaTime) {
        player.updateState(deltaTime);
        objects.forEach(mapObject -> mapObject.updateState(deltaTime));
    }

    public List<MapObject> getObjects() {
        return objects;
    }

    public MapObject getPlayer() {
        return player;
    }
}
