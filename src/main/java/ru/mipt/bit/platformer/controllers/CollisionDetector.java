package ru.mipt.bit.platformer.controllers;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.common.ObjectAddHandler;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetector implements ObjectAddHandler {
    private static final List<MapObject> objects = new ArrayList<>();

    public CollisionDetector() {}

    public void add(MapObject object) {
        objects.add(object);
    }

    static public boolean collides(MapObject object, GridPoint2 targetCoordinates) {
        for (MapObject other: objects) {
            if (other != object) {
                if (targetCoordinates.equals(other.getCoordinates())) {
                    return true;
                }
            }
        }
        return false;
    }
}
