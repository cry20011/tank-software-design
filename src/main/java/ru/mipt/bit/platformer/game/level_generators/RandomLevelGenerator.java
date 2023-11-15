package ru.mipt.bit.platformer.game.level_generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.*;
import ru.mipt.bit.platformer.game.action_generators.KeyboadTanksController;
import ru.mipt.bit.platformer.game.entities.Player;
import ru.mipt.bit.platformer.game.entities.Tank;
import ru.mipt.bit.platformer.game.entities.Tree;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomLevelGenerator implements LevelGenerator {
    private final int width;
    private final int height;
    private final int maxTanksCount;

    private final List<LevelListener> handlerList = new ArrayList<>();
    private final List<ObjectsController> controllers = new ArrayList<>();
    private Player player;

    public RandomLevelGenerator(int width, int height, int maxTanksCount, List<ObjectsController> controllers, List<LevelListener> handlers) {
        this.width = width;
        this.height = height;
        this.maxTanksCount = maxTanksCount;

        this.controllers.addAll(controllers);
        this.handlerList.addAll(handlers);
    }

    private List<GameObject> generateObjects() {
        List<GameObject> objects = new ArrayList<>();

        Set<GridPoint2> generated = new HashSet<>();

        GridPoint2 playerCoordinates = genRandomGridPoint2(width, height);
        generated.add(playerCoordinates);

        this.player = new Player(playerCoordinates);

        genObstacles(objects, generated);
        genTanks(objects, generated);

        return objects;
    }

    private void genObstacles(List<GameObject> objects, Set<GridPoint2> generated) {
        int genCount = ThreadLocalRandom.current().nextInt(1, width * height - 1);
        for (int i = 0; i < genCount; i++) {
            GridPoint2 coordinates = genRandomGridPoint2(width, height);
            if (!generated.contains(coordinates)) {
                generated.add(coordinates);
                objects.add(new Tree(coordinates));
            }
        }
    }

    private void genTanks(List<GameObject> objects, Set<GridPoint2> generated) {
        int genCount = ThreadLocalRandom.current().nextInt(1, maxTanksCount);
        for (int i = 0; i < genCount; i++) {
            GridPoint2 coordinates = genRandomGridPoint2(width, height);
            if (!generated.contains(coordinates)) {
                generated.add(coordinates);
                objects.add(new Tank(coordinates));
            }
        }
    }

    private GridPoint2 genRandomGridPoint2(int width, int height) {
        int x = ThreadLocalRandom.current().nextInt(0, width - 1);
        int y = ThreadLocalRandom.current().nextInt(0, height - 1);
        return new GridPoint2(x, y);

    }

    @Override
    public Level generate() {
        List<GameObject> objects = generateObjects();

        KeyboadTanksController playerController = new KeyboadTanksController();
        controllers.add(playerController);

        Level level = new Level(controllers, handlerList);

        level.addPlayer(this.player);
        objects.forEach(level::add);

        return level;
    }
}
