package ru.mipt.bit.platformer.common;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.controllers.InputController;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.entities.Tank;
import ru.mipt.bit.platformer.entities.Tree;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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
        Map.Entry<Instruction, MapObject> objectInstruction = inputController.getInstruction();
        if (objectInstruction != null) {
            objectInstruction.getKey().apply(objectInstruction.getValue());
        }
    }

    public void updateState(float deltaTime) {
        objects.forEach(mapObject -> mapObject.updateState(deltaTime));
    }

    public MapObject getPlayer() {
        return player;
    }
}
