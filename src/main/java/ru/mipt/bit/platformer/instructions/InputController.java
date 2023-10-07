package ru.mipt.bit.platformer.instructions;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.entities.MapObject;

import java.util.HashMap;
import java.util.Map;


public class InputController {
    private final Map<Integer, Map.Entry<Instruction, MapObject>> keyToInstructionMap = new HashMap<>();

    private final MapObject player;

    public InputController(MapObject player) {
        this.player = player;
    }

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