package ru.mipt.bit.platformer.game.action_generators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.game.ActionGenerator;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.actions.Direction;
import ru.mipt.bit.platformer.game.Action;

import java.util.*;


public class KeyboadActionGenerator implements ActionGenerator {
    private static final Map<Integer, Action> keyToInstructionMap = new HashMap<>();

    public KeyboadActionGenerator() {
        initMappings();
    }

    public void addMapping(int key, Action action) {
        keyToInstructionMap.put(key, action);
    }

    void initMappings() {
        addMapping(Input.Keys.UP, Direction.UP);
        addMapping(Input.Keys.W, Direction.UP);
        addMapping(Input.Keys.LEFT, Direction.LEFT);
        addMapping(Input.Keys.A, Direction.LEFT);
        addMapping(Input.Keys.DOWN, Direction.DOWN);
        addMapping(Input.Keys.S, Direction.DOWN);
        addMapping(Input.Keys.RIGHT, Direction.RIGHT);
        addMapping(Input.Keys.D, Direction.RIGHT);
    }

    @Override
    public Action generateFor(MapObject object) {
        for (Integer key : keyToInstructionMap.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                return keyToInstructionMap.get(key);
            }
        }

        return null;
    }
}