package ru.mipt.bit.platformer.controllers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.common.ObjectAddHandler;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetector implements ObjectAddHandler {
    static private int width;
    static private int height;

    private static final List<MapObject> objects = new ArrayList<>();

    public CollisionDetector(int width, int height) {
        CollisionDetector.width = width;
        CollisionDetector.height = height;
    }

    public void add(MapObject object) {
        objects.add(object);
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
