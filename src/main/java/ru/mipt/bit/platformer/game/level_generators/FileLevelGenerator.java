package ru.mipt.bit.platformer.game.level_generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.*;
import ru.mipt.bit.platformer.game.action_generators.KeyboadTanksController;
import ru.mipt.bit.platformer.game.action_generators.RandomTanksController;
import ru.mipt.bit.platformer.game.entities.Player;
import ru.mipt.bit.platformer.game.entities.Tank;
import ru.mipt.bit.platformer.game.entities.Tree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileLevelGenerator implements LevelGenerator {
    private final String path;
    private final List<LevelListener> handlerList = new ArrayList<>();
    private Player player;

    public FileLevelGenerator(String path, List<LevelListener> handlers) {
        this.path = path;
        this.handlerList.addAll(handlers);
    }

    private List<GameObject> getObjectsFromFile() {
        List<GameObject> objects = new ArrayList<>();

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

    private void setObjectByChar(List<GameObject> objects, char c, int x, int y) {
        if (c == 'X') {
            this.player = new Player(new GridPoint2(x, y));
        } else if (c == 'T') {
            objects.add(new Tree(new GridPoint2(x, y)));
        } else if (c == 'E') {
            objects.add(new Tank(new GridPoint2(x, y)));
        }
    }

    @Override
    public Level generate() {
        List<GameObject> objects = getObjectsFromFile();

        List<ObjectsController> controllers = List.of(
                new KeyboadTanksController(),
                new RandomTanksController()
        );

        Level level = new Level(controllers, handlerList);

        level.addPlayer(player);
        objects.forEach(level::add);

        return level;
    }
}
