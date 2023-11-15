package ru.mipt.bit.platformer.game.action_generators.ai_adapter_util;

import org.awesome.ai.state.GameState;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.actions.Direction;
import ru.mipt.bit.platformer.game.actions.Shoot;

import java.util.ArrayList;
import java.util.List;

public class AiGameEntitiesConverter {
    public static Action convertAiAction(org.awesome.ai.Action action) {
        return switch (action) {
            case Shoot -> new Shoot();
            case MoveNorth -> Direction.UP;
            case MoveWest -> Direction.RIGHT;
            case MoveEast -> Direction.LEFT;
            case MoveSouth -> Direction.DOWN;
        };
    }

    public static Bot mapObjectToBot(GameObject object) {
        return new Bot.BotBuilder()
                .source(object)
                .x(object.getCoordinates().x)
                .y(object.getCoordinates().y)
                .destX(object.getCoordinates().x)
                .destY(object.getCoordinates().y)
                .orientation(degreesToOrientation(object.getDirection().getRotation()))
                .build();
    }

    public static GameState makeGameState(List<GameObject> objects, int width, int height) {
        return new GameState.GameStateBuilder()
                .bots(mapObjectsToBots(objects))
                .levelWidth(width)
                .levelHeight(height)
                .build();
    }

    public static List<Bot> mapObjectsToBots(List<GameObject> objects) {
        List<Bot> bots = new ArrayList<>();
        objects.forEach(object -> bots.add(mapObjectToBot(object)));
        return bots;
    }

    public static Orientation degreesToOrientation(float degrees) {
        return switch ((int) degrees) {
            case 90 -> Orientation.N;
            case 0 -> Orientation.W;
            case -180 -> Orientation.E;
            case -90 -> Orientation.S;
            default -> Orientation.N;
        };
    }
}
