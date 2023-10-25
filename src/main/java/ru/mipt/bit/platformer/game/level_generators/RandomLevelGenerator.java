package ru.mipt.bit.platformer.game.level_generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.Level;
import ru.mipt.bit.platformer.game.LevelGenerator;
import ru.mipt.bit.platformer.game.ObjectAddHandler;
import ru.mipt.bit.platformer.game.action_generators.KeyboadActionGenerator;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.action_generators.RandomActionGenerator;
import ru.mipt.bit.platformer.game.entities.Tank;
import ru.mipt.bit.platformer.game.entities.Tree;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomLevelGenerator implements LevelGenerator {
    private final int width;
    private final int height;
    private final int maxTanksCount;
    private final List<ObjectAddHandler> handlerList = new ArrayList<>();
    private MapObject player;

    public RandomLevelGenerator(int width, int height, int maxTanksCount, List<ObjectAddHandler> handlers) {
        this.width = width;
        this.height = height;
        this.maxTanksCount = maxTanksCount;
        this.handlerList.addAll(handlers);
    }

    private List<MapObject> generateObjects() {
        List<MapObject> objects = new ArrayList<>();

        Set<GridPoint2> generated = new HashSet<>();

        GridPoint2 playerCoordinates = genRandomGridPoint2(width, height);
        generated.add(playerCoordinates);

        this.player = new Tank(playerCoordinates, new KeyboadActionGenerator());

        genObstacles(objects, generated);
        genTanks(objects, generated);

        return objects;
    }

    private void genObstacles(List<MapObject> objects, Set<GridPoint2> generated) {
        int genCount = ThreadLocalRandom.current().nextInt(1, width * height - 1);
        for (int i = 0; i < genCount; i++) {
            GridPoint2 coordinates = genRandomGridPoint2(width, height);
            if (!generated.contains(coordinates)) {
                generated.add(coordinates);
                objects.add(new Tree(coordinates));
            }
        }
    }

    private void genTanks(List<MapObject> objects, Set<GridPoint2> generated) {
        int genCount = ThreadLocalRandom.current().nextInt(1, maxTanksCount);
        for (int i = 0; i < genCount; i++) {
            GridPoint2 coordinates = genRandomGridPoint2(width, height);
            if (!generated.contains(coordinates)) {
                generated.add(coordinates);
                objects.add(new Tank(coordinates, new RandomActionGenerator()));
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
        List<MapObject> objects = generateObjects();
        Level level = new Level(player, handlerList);

        objects.forEach(level::add);

        return level;
    }
}
