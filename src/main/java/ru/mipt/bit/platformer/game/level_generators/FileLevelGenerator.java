package ru.mipt.bit.platformer.game.level_generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.Level;
import ru.mipt.bit.platformer.game.LevelGenerator;
import ru.mipt.bit.platformer.game.ObjectAddHandler;
import ru.mipt.bit.platformer.game.action_generators.KeyboadActionGenerator;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.entities.Tank;
import ru.mipt.bit.platformer.game.entities.Tree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileLevelGenerator implements LevelGenerator {
    private final String path;
    private final List<ObjectAddHandler> handlerList = new ArrayList<>();
    private MapObject player;

    public FileLevelGenerator(String path, List<ObjectAddHandler> handlers) {
        this.path = path;
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
            this.player = new Tank(new GridPoint2(x, y), new KeyboadActionGenerator());
        } else if (c == 'T') {
            objects.add(new Tree(new GridPoint2(x, y)));
        }
    }

    @Override
    public Level generate() {
        List<MapObject> objects = getObjectsFromFile();
        Level level = new Level(player, handlerList);

        objects.forEach(level::add);

        return level;
    }
}
