package ru.mipt.bit.platformer.game.action_generators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.game.ObjectsController;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.game.actions.Direction;
import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.actions.Shoot;
import ru.mipt.bit.platformer.game.entities.Tank;

import java.util.*;


public class KeyboadTanksController implements ObjectsController, LevelListener {
    private static final Map<Integer, Action> keyToInstructionMap = new HashMap<>();

    private final Tank object;

    public KeyboadTanksController(Tank object) {
        this.object = object;
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

        addMapping(Input.Keys.SPACE, new Shoot());
    }

    @Override
    public Map<MapObject, Action> nextActions() {
        for (Integer key : keyToInstructionMap.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                return Map.of(object, keyToInstructionMap.get(key));
            }
        }

        return Collections.emptyMap();
    }

    @Override
    public void add(MapObject object) {}

    @Override
    public void remove(MapObject object) {}
}