package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.common.Level;
import ru.mipt.bit.platformer.common.ObjectAddHandler;
import ru.mipt.bit.platformer.controllers.InputController;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.entities.Tank;
import ru.mipt.bit.platformer.entities.Tree;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class RandomLevelGenerator implements LevelGenerator {
    private final int width;
    private final int height;
    private final List<ObjectAddHandler> handlerList = new ArrayList<>();
    private final InputController inputController;
    private MapObject player;

    public RandomLevelGenerator(int width, int height, InputController inputController, List<ObjectAddHandler> handlers) {
        this.width = width;
        this.height = height;
        this.inputController = inputController;
        this.handlerList.addAll(handlers);
    }

    private List<MapObject> generateObjects() {
        List<MapObject> objects = new ArrayList<>();

        Set<GridPoint2> generated = new HashSet<>();

        GridPoint2 playerCoordinates = genRandomGridPoint2(width, height);
        generated.add(playerCoordinates);

        this.player = new Tank(playerCoordinates);

        genObstacles(objects, generated);

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

    private GridPoint2 genRandomGridPoint2(int width, int height) {
        int x = ThreadLocalRandom.current().nextInt(0, width - 1);
        int y = ThreadLocalRandom.current().nextInt(0, height - 1);
        return new GridPoint2(x, y);

    }

    @Override
    public Level generate() {
        List<MapObject> objects = generateObjects();
        Level level = new Level(player, inputController, handlerList);

        objects.forEach(level::add);

        return level;
    }
}
