package ru.mipt.bit.platformer.entities;

import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.entities.Tank;
import ru.mipt.bit.platformer.graphics.GraphicsController;
import ru.mipt.bit.platformer.instructions.InputController;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Level {
    private final GraphicsController graphicsController;
    private final InputController inputController;

    private final Tank player;
    private final List<MapObject> objects = new ArrayList<>();

    public Level(Tank player, GraphicsController graphicsController, InputController inputController) {
        this.player = player;
        this.graphicsController = graphicsController;
        this.inputController = inputController;

        add(player);
    }

    public void add(MapObject object) {
        graphicsController.addGraphicsOf(object);
        objects.add(object);
    }

    public void moveObjects(float deltaTime) {
        Map.Entry<Instruction, MapObject> objectInstruction = inputController.getInstruction();

        if (objectInstruction != null) {
            objectInstruction.getValue().apply(objectInstruction.getKey(), objects);
        }

        graphicsController.moveRectangles();

        player.updateState(deltaTime);
    }

}
