package ru.mipt.bit.platformer.game.action_generators;

import ru.mipt.bit.platformer.game.ActionGenerator;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.actions.Direction;
import ru.mipt.bit.platformer.game.Action;

public class RandomActionGenerator implements ActionGenerator {
    public RandomActionGenerator() {}

    @Override
    public Action generateFor(MapObject object) {
        return Direction.randomDirection();
    }
}
