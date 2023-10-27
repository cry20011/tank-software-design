package ru.mipt.bit.platformer.game.action_generators.random_actions_generator_util;

import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.actions.Direction;
import ru.mipt.bit.platformer.game.actions.Shoot;
import ru.mipt.bit.platformer.game.entities.Tank;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public enum TankActions {
    MOVE(Direction::randomDirection),
    SHOOT(Shoot::new);

    private final Supplier<? extends Action> randomGenFunction;

    public static Action randomTankAction()  {
        TankActions[] actions = values();
        return actions[ThreadLocalRandom.current().nextInt(0, actions.length)].randomGenFunction.get();
    }

    TankActions(Supplier<? extends Action> randomGenFunction) {
        this.randomGenFunction = randomGenFunction;
    }
}
