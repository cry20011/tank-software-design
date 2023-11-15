package ru.mipt.bit.platformer.game.game_engine;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.game.entities.Player;
import ru.mipt.bit.platformer.game.entities.Tree;

import java.util.ArrayList;
import java.util.List;

public class CollisionDetector implements LevelListener {
    static private int width;
    static private int height;

    private static final List<GameObject> objects = new ArrayList<>();

    public CollisionDetector(int width, int height) {
        CollisionDetector.width = width;
        CollisionDetector.height = height;
    }

    @Override
    public void add(GameObject object) {
        objects.add(object);
    }

    @Override
    public void addPlayer(Player player) {
        add(player);
    }

    @Override
    public void remove(GameObject object) {
        objects.remove(object);
    }

    static public GameObject getCollidedObject(GameObject object, GridPoint2 targetCoordinates) {
        if (!inArea(targetCoordinates)) {
            return new Tree(targetCoordinates);
        }
        for (GameObject other : objects) {
            if (other != object) {
                if (collidedWithOther(object, other, targetCoordinates)) {
                    return other;
                }
            }
        }
        return null;
    }

    static private boolean inArea(GridPoint2 coordinates) {
        return coordinates.x >= 0 && coordinates.x < width && coordinates.y >= 0 && coordinates.y < height;
    }

    static private boolean collidedWithOther(GameObject object, GameObject other, GridPoint2 targetCoordinates) {
        return object.getCoordinates().equals(other.getCoordinates())
                || targetCoordinates.equals(other.getCoordinates())
                || targetCoordinates.equals(other.getDestinationCoordinates());
    }
}
