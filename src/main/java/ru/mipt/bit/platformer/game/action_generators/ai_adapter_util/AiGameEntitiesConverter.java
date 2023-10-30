package ru.mipt.bit.platformer.game.action_generators.ai_adapter_util;

import org.awesome.ai.state.GameState;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.actions.Direction;

import java.util.ArrayList;
import java.util.List;

public class AiGameEntitiesConverter {
    public static Action convertAiAction(org.awesome.ai.Action action) {
        switch (action) {
            case MoveNorth:
                return Direction.UP;
            case MoveWest:
                return Direction.RIGHT;
            case MoveEast:
                return Direction.LEFT;
            case MoveSouth:
                return Direction.DOWN;
            default:
                return null;
        }
    }

    public static Bot mapObjectToBot(MapObject object) {
        return new Bot.BotBuilder()
                .source(object)
                .x(object.getCoordinates().x)
                .y(object.getCoordinates().y)
                .destX(object.getCoordinates().x)
                .destY(object.getCoordinates().y)
                .orientation(degreesToOrientation(object.getDirection().getRotation()))
                .build();
    }

    public static GameState gameStateFromLevel(List<MapObject> objects, int width, int height) {
        return new GameState.GameStateBuilder()
                .bots(mapObjectsToBots(objects))
                .levelWidth(width)
                .levelHeight(height)
                .build();
    }

    public static List<Bot> mapObjectsToBots(List<MapObject> objects) {
        List<Bot> bots = new ArrayList<>();
        objects.forEach(object -> bots.add(mapObjectToBot(object)));
        return bots;
    }

    public static Orientation degreesToOrientation(float degrees) {
        switch ((int) degrees) {
            case 90:
                return Orientation.N;
            case 0:
                return Orientation.W;
            case -180:
                return Orientation.E;
            case -90:
                return Orientation.S;
        }
        return Orientation.N;
    }
}
