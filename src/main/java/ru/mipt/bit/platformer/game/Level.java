package ru.mipt.bit.platformer.game;

import java.util.*;

public class Level {
    private final List<ObjectAddHandler> objectAddHandlers = new ArrayList<>();

    private final MapObject player;
    private final List<MapObject> objects = new ArrayList<>();

    public Level(MapObject player, List<ObjectAddHandler> objectAddHandlers) {
        this.objectAddHandlers.addAll(objectAddHandlers);

        this.player = player;
        objectAddHandlers.forEach(objectAddHandler -> objectAddHandler.add(player));
    }

    public void add(MapObject object) {
        objectAddHandlers.forEach(objectAddHandler -> objectAddHandler.add(object));
        objects.add(object);
    }

    public void applyAction() {
        player.applyNextAction();
        objects.forEach(MapObject::applyNextAction);
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
