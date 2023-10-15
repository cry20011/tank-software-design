package ru.mipt.bit.platformer.generators;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.common.Level;
import ru.mipt.bit.platformer.common.ObjectAddHandler;
import ru.mipt.bit.platformer.controllers.InputController;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.entities.Tank;
import ru.mipt.bit.platformer.entities.Tree;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileLevelGenerator implements LevelGenerator {

    private static final Map<Character, Constructor<? extends MapObject>> charToConstructorMap = new HashMap<>();
    private static void initCharToConstructorMap() {
        try {
            charToConstructorMap.put('T', Tree.class.getConstructor(GridPoint2.class));
            charToConstructorMap.put('X', Tank.class.getConstructor(GridPoint2.class));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private final String path;
    private final List<ObjectAddHandler> handlerList = new ArrayList<>();
    private final InputController inputController;
    private MapObject player;

    public FileLevelGenerator(String path, InputController inputController, List<ObjectAddHandler> handlers) {
        this.path = path;
        this.handlerList.addAll(handlers);
        this.inputController = inputController;

        initCharToConstructorMap();
    }

    private List<MapObject> getObjectsFromFile() {
        List<MapObject> objects = new ArrayList<>();

        try {
            List<String> allLines = Files.readAllLines(Paths.get(path));

            for (int i = allLines.size() - 1; i >= 0; --i) {
                String line = allLines.get(i);
                for (int x = 0; x < line.length(); x++) {
                    setObjectByChar(objects, line.charAt(x), x,
                            allLines.size() - i - 1);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return objects;
    }

    private void setObjectByChar(List<MapObject> level, char c, int x, int y) {
        try {
            Constructor<? extends MapObject> constructor = charToConstructorMap.get(c);
            if (constructor == null) return;
            constructor.setAccessible(true);
            MapObject object = constructor.newInstance(new GridPoint2(x, y));
            level.add(object);

            if (c == 'X') {
                this.player = object;
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Level generate() {
        List<MapObject> objects = getObjectsFromFile();
        Level level = new Level(player, inputController, handlerList);

        objects.forEach(level::add);

        return level;
    }
}
