package ru.mipt.bit.platformer.common;

import ru.mipt.bit.platformer.controllers.InputController;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Level {
    private final List<ObjectAddHandler> handlerList = new ArrayList<>();
    private final InputController inputController;

    private final MapObject player;
    private final List<MapObject> objects = new ArrayList<>();

    public Level(MapObject player, InputController inputController, ObjectAddHandler... handlers) {
        this.player = player;
        this.handlerList.addAll(List.of(handlers));
        this.inputController = inputController;

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
}
