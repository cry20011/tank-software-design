package ru.mipt.bit.platformer.game.graphics.graphics_actions_generators;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import ru.mipt.bit.platformer.game.entities.Tank;
import ru.mipt.bit.platformer.game.graphics.GraphicsObject;
import ru.mipt.bit.platformer.game.graphics.GraphicsAction;
import ru.mipt.bit.platformer.game.graphics.GraphicsController;
import ru.mipt.bit.platformer.game.graphics.GraphicsListener;
import ru.mipt.bit.platformer.game.graphics.graphics_actions.ShowHealth;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KeyboardTankGraphicsController implements GraphicsController, GraphicsListener {
    private final Map<Integer, GraphicsAction> keyToActionMap = new HashMap<>();
    private final List<GraphicsObject> graphicsObjectList = new ArrayList<>();

    public void addMapping(int key, GraphicsAction action) {
        keyToActionMap.put(key, action);
    }
    void initMappings() {
        addMapping(Input.Keys.L, new ShowHealth());
    }
    public KeyboardTankGraphicsController() {
        initMappings();
    }

    @Override
    public Map<GraphicsObject, GraphicsAction> nextActions() {
        for (Integer key : keyToActionMap.keySet()) {
            if (Gdx.input.isKeyPressed(key)) {
                return graphicsObjectList.stream().collect(Collectors.toMap(Function.identity(), graphics -> keyToActionMap.get(key)));
            }
        }

        return Collections.emptyMap();
    }

    @Override
    public void add(GraphicsObject graphicsObject) {
        if (graphicsObject.getObject() instanceof Tank) {
            graphicsObjectList.add(graphicsObject);
        }
    }

    @Override
    public void remove(GraphicsObject graphicsObject) {
        if (graphicsObject.getObject() instanceof Tank) {
            graphicsObjectList.remove(graphicsObject);
        }
    }
}
