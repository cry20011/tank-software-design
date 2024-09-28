package ru.mipt.bit.platformer.game.action_generators;

import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.ObjectsController;
import ru.mipt.bit.platformer.game.entities.Bullet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BulletsController implements ObjectsController {
    private final List<Bullet> bullets = new ArrayList<>();

    @Override
    public void add(MapObject object) {
        if (object instanceof Bullet bullet) {
            bullets.add(bullet);
        }
    }

    @Override
    public void remove(MapObject object) {
        if (object instanceof Bullet bullet) {
            bullets.remove(bullet);
        }
    }

    @Override
    public Map<MapObject, Action> nextActions() {
        return bullets.stream().collect(Collectors.toMap(Function.identity(), Bullet::getDirection));
    }
}
