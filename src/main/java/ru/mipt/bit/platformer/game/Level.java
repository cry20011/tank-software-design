package ru.mipt.bit.platformer.game;

import ru.mipt.bit.platformer.game.entities.Player;

import java.util.*;

public class Level {
    private static Level level = null;
    public static Level get() {
        return level;
    }

    private final List<ObjectsController> objectControllers = new ArrayList<>();
    private final List<LevelListener> levelListeners = new ArrayList<>();

    private Player player;
    private final List<GameObject> objects = new ArrayList<>();

    private final List<GameObject> destroyedObjects = new ArrayList<>();

    public Level(List<ObjectsController> objectControllers, List<LevelListener> levelListeners) {
        level = this;

        this.levelListeners.addAll(levelListeners);
        this.objectControllers.addAll(objectControllers);
    }

    public void add(GameObject object) {
        objectControllers.forEach(controller -> controller.add(object));
        levelListeners.forEach(handler -> handler.add(object));
        objects.add(object);
    }

    public void addPlayer(Player player) {
        objectControllers.forEach(controller -> controller.addPlayer(player));
        levelListeners.forEach(handler -> handler.addPlayer(player));
        this.player = player;
    }

    public void addToDestroyed(GameObject object) {
        destroyedObjects.add(object);
    }

    public void applyActions() {
        objectControllers.forEach(controller -> controller.nextActions().forEach((object, action) -> action.apply(object)));
    }

    public void updateState(float deltaTime) {
        applyActions();

        player.updateState(deltaTime);

        objects.forEach(mapObject -> mapObject.updateState(deltaTime));

        destroyedObjects.forEach(this::remove);
    }

    private void remove(GameObject object) {
        objectControllers.forEach(controller -> controller.remove(object));
        levelListeners.forEach(listener -> listener.remove(object));
        objects.remove(object);
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public GameObject getPlayer() {
        return player;
    }
}
