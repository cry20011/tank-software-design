package ru.mipt.bit.platformer.game.action_generators;

import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.game.ObjectsController;
import ru.mipt.bit.platformer.game.action_generators.random_actions_generator_util.RandomTankActionGenerator;
import ru.mipt.bit.platformer.game.entities.Player;
import ru.mipt.bit.platformer.game.entities.Tank;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RandomTanksController implements ObjectsController, LevelListener {
    private final List<Tank> objects = new ArrayList<>();
    private final long GENERATION_INTERVAL_MILLIS = 250;
    private LocalTime lastGenTime = LocalTime.now();

    public RandomTanksController() {
    }

    @Override
    public Map<GameObject, Action> nextActions() {
        if (readyToGenerate()) {
            lastGenTime = LocalTime.now();
            return objects.stream().collect(Collectors.toMap(Function.identity(), it -> RandomTankActionGenerator.generate()));

        }
        return Collections.emptyMap();
    }

    private boolean readyToGenerate() {
        return LocalTime.now().isAfter(lastGenTime.plus(GENERATION_INTERVAL_MILLIS, ChronoUnit.MILLIS));
    }

    @Override
    public void add(GameObject object) {
        if (object instanceof Tank tank) {
            objects.add(tank);
        }
    }

    @Override
    public void addPlayer(Player player) {}

    @Override
    public void remove(GameObject object) {
        if (object instanceof Tank tank) {
            objects.remove(tank);
        }
    }
}
