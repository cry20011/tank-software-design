package ru.mipt.bit.platformer.game.game_engine;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.LevelListener;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetector implements LevelListener {
    static private int width;
    static private int height;

    private static final List<MapObject> objects = new ArrayList<>();

    public CollisionDetector(int width, int height) {
        CollisionDetector.width = width;
        CollisionDetector.height = height;
    }

    @Override
    public void add(MapObject object) {
        objects.add(object);
    }

    @Override
    public void remove(MapObject object) {
        objects.remove(object);
    }

    static public boolean collides(MapObject object, GridPoint2 targetCoordinates) {
        for (MapObject other : objects) {
            if (other != object) {
                if (targetCoordinates.equals(other.getCoordinates()) || targetCoordinates.equals(other.getDestinationCoordinates()) || !inArea(targetCoordinates)) {
                    return true;
                }
            }
        }
        return false;
    }

    static private boolean inArea(GridPoint2 coordinates) {
        return coordinates.x >= 0 && coordinates.x < width && coordinates.y >= 0 && coordinates.y < height;
    }
}
