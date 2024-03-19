package ru.mipt.bit.platformer.game.action_generators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.game.ObjectsController;
import ru.mipt.bit.platformer.game.actions.Direction;
import ru.mipt.bit.platformer.game.actions.Shoot;
import ru.mipt.bit.platformer.game.actions.SwitchToggle;
import ru.mipt.bit.platformer.game.entities.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class KeyboadTanksController implements ObjectsController, LevelListener {
    private static final Map<Integer, Action> keyToActionMap = new HashMap<>();

    private Player player;

    public KeyboadTanksController() {
        initMappings();
    }

    public void addMapping(int key, Action action) {
        keyToActionMap.put(key, action);
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
        addMapping(Input.Keys.L, new SwitchToggle());
    }

    @Override
    public Map<GameObject, Action> nextActions() {
        for (Integer key : keyToActionMap.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                return Map.of(player, keyToActionMap.get(key));
            }
        }

        return Collections.emptyMap();
    }

    @Override
    public void add(GameObject object) {
    }

    @Override
    public void addPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void remove(GameObject object) {
    }
}
