package ru.mipt.bit.platformer.instructions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.instructions.Direction;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.util.HashMap;
import java.util.Map;


public class InputController {
    private final Map<Integer, Map.Entry<Instruction, MapObject>> keyToInstructionMap = new HashMap<>();

    private final MapObject player;

    public InputController(MapObject player) {
        this.player = player;

        initMappings();
    }

    private void initMappings() {
        addMapping(Input.Keys.UP, Direction.UP, player);
        addMapping(Input.Keys.W, Direction.UP, player);
        addMapping(Input.Keys.LEFT, Direction.LEFT, player);
        addMapping(Input.Keys.A, Direction.LEFT, player);
        addMapping(Input.Keys.DOWN, Direction.DOWN, player);
        addMapping(Input.Keys.S, Direction.DOWN, player);
        addMapping(Input.Keys.RIGHT, Direction.RIGHT, player);
        addMapping(Input.Keys.D, Direction.RIGHT, player);

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