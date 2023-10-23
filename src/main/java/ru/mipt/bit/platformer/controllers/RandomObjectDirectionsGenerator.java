package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.common.ObjectAddHandler;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.instructions.Direction;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomObjectDirectionsGenerator implements ObjectAddHandler, InstructionsGenerator {
    private final List<MapObject> objects = new ArrayList<>();

    public RandomObjectDirectionsGenerator() {}

    @Override
    public void add(MapObject object) {
        objects.add(object);
    }

    @Override
    public Map<MapObject, Instruction> generate() {
        Map<MapObject, Instruction> instructions = new HashMap<>();
        objects.forEach(object -> instructions.put(object, Direction.randomDirection()));
        return instructions;
    }
}
