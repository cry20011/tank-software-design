package ru.mipt.bit.platformer.common;

import ru.mipt.bit.platformer.controllers.InputController;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.util.*;

public class Level {
    private final List<ObjectAddHandler> handlerList = new ArrayList<>();
    private final InputController inputController;
    private final List<MapObject> objects = new ArrayList<>();

    private final MapObject player;

    public Level(MapObject player, InputController inputController, List<ObjectAddHandler> handlers) {
        this.handlerList.addAll(handlers);
        this.inputController = inputController;
        this.player = player;
        add(player);
    }

    public void add(MapObject object) {
        handlerList.forEach(objectAddHandler -> objectAddHandler.add(object));
        objects.add(object);
    }

    public void applyInstructions() {
        inputController.getInstructions().forEach((object, instruction) -> instruction.apply(object));
    }

    public void updateState(float deltaTime) {
        objects.forEach(mapObject -> mapObject.updateState(deltaTime));
    }

    public List<MapObject> getObjects() {
        return objects;
    }

    public MapObject getPlayer() {
        return player;
    }
}
