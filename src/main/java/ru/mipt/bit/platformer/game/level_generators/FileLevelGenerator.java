package ru.mipt.bit.platformer.game.level_generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.*;
import ru.mipt.bit.platformer.game.action_generators.KeyboadTanksController;
import ru.mipt.bit.platformer.game.entities.Tank;
import ru.mipt.bit.platformer.game.entities.Tree;
import ru.mipt.bit.platformer.game.graphics.GraphicsController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileLevelGenerator implements LevelGenerator {
    private final String path;
    private final List<ObjectsController> controllers = new ArrayList<>();
    private final List<GraphicsController> graphicsControllers = new ArrayList<>();
    private final List<LevelListener> handlerList = new ArrayList<>();
    private Tank player;

    public FileLevelGenerator(String path, List<ObjectsController> controllers, List<LevelListener> handlers) {
        this.path = path;
        this.controllers.addAll(controllers);
        this.handlerList.addAll(handlers);
    }

    private List<MapObject> getObjectsFromFile() {
        List<MapObject> objects = new ArrayList<>();

        try {
            List<String> allLines = Files.readAllLines(Paths.get(path));

            for (int i = allLines.size() - 1; i >= 0; --i) {
                String line = allLines.get(i);
                for (int x = 0; x < line.length(); x++) {
                    setObjectByChar(objects, line.charAt(x), x, allLines.size() - i - 1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return objects;
    }

    private void setObjectByChar(List<MapObject> objects, char c, int x, int y) {
        if (c == 'X') {
            this.player = new Tank(new GridPoint2(x, y));
        } else if (c == 'T') {
            objects.add(new Tree(new GridPoint2(x, y)));
        } else if (c == 'E') {
            objects.add(new Tank(new GridPoint2(x, y)));
        }
    }

    @Override
    public Level generate() {
        List<MapObject> objects = getObjectsFromFile();

        KeyboadTanksController playerController = new KeyboadTanksController(this.player);
        controllers.add(playerController);

        Level level = new Level(player, controllers, handlerList);

        objects.forEach(level::add);

        return level;
    }
}
