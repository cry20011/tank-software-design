package ru.mipt.bit.platformer.controllers;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.util.*;


public class InputController {
    private final List<InstructionsGenerator> generators;

    private final Map<Integer, Map.Entry<Instruction, MapObject>> keyToInstructionMap = new HashMap<>();

    public InputController(List<InstructionsGenerator> generators) {
        this.generators = generators;
    }

    public void addMapping(int key, Instruction instruction, MapObject object) {
        keyToInstructionMap.put(key, Map.entry(instruction, object));
    }

    public Map<MapObject, Instruction> getInstructions() {
        Map<MapObject, Instruction> instructions = new HashMap<>();
        for (InstructionsGenerator generator: generators) {
            instructions.putAll(generator.generate());
        }

        keyToInstructionMap.values().forEach(entry -> instructions.remove(entry.getValue()));

        for (Integer key : keyToInstructionMap.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                var entry = keyToInstructionMap.get(key);
                instructions.put(entry.getValue(), entry.getKey());
            }
        }

        return instructions;
    }
}