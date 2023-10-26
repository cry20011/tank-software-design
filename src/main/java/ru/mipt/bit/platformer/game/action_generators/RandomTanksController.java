package ru.mipt.bit.platformer.game.action_generators;

import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.game.ObjectsController;
import ru.mipt.bit.platformer.game.actions.Direction;
import ru.mipt.bit.platformer.game.entities.Tank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RandomTanksController implements ObjectsController, LevelListener {
    private final List<Tank> objects = new ArrayList<>();

    @Override
    public Map<MapObject, Action> nextActions() {
        return objects.stream().collect(Collectors.toMap(Function.identity(), it -> Direction.randomDirection()));
    }

    @Override
    public void add(MapObject object) {
        if (object instanceof Tank tank) {
            objects.add(tank);
        }
    }

    @Override
    public void remove(MapObject object) {
        if (object instanceof Tank tank) {
            objects.remove(tank);
        }
    }
}
