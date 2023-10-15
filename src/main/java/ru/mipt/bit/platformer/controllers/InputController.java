package ru.mipt.bit.platformer.controllers;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.util.HashMap;
import java.util.Map;


public class InputController {
    private final Map<Integer, Map.Entry<Instruction, MapObject>> keyToInstructionMap = new HashMap<>();

    public InputController() {}

    public void addMapping(int key, Instruction instruction, MapObject object) {
        keyToInstructionMap.put(key, Map.entry(instruction, object));
    }

    public Map.Entry<Instruction, MapObject> getInstruction() {
        for (Integer key : keyToInstructionMap.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                return keyToInstructionMap.get(key);
            }
        }
        return null;
    }
}