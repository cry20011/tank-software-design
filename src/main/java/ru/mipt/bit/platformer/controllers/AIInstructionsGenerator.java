package ru.mipt.bit.platformer.controllers;


import org.awesome.ai.AI;
import org.awesome.ai.Action;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.state.movable.Player;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.common.Level;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.instructions.Direction;
import ru.mipt.bit.platformer.instructions.Instruction;;import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AIInstructionsGenerator implements InstructionsGenerator {

    private final Level level;
    private final int width;
    private final int height;

    private final AI notRecommendingAI = new NotRecommendingAI();

    public AIInstructionsGenerator(Level level, int width, int height) {
        this.level = level;
        this.width = width;
        this.height = height;
    }

    private Orientation degreesToOrientation(float degrees) {
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

    private GameState gameStateFromLevel(Level level) {
        Player player = new Player.PlayerBuilder()
                .source(level.getPlayer())
                .x(level.getPlayer().getCoordinates().x)
                .y(level.getPlayer().getCoordinates().y)
                .destX(level.getPlayer().getCoordinates().x)
                .destY(level.getPlayer().getCoordinates().y)
                .orientation(degreesToOrientation(level.getPlayer().getRotation()))
                .build();

        return new GameState.GameStateBuilder()
                .player(player)
                .bots(mapObjectsToBots(level.getObjects()))
                .levelWidth(width)
                .levelHeight(height)
                .build();
    }

    private List<Bot> mapObjectsToBots(List<MapObject> objects) {
        List<Bot> bots = new ArrayList<>();
        objects.forEach(object -> bots.add(mapObjectToBot(object)));
        return bots;
    }

    private Bot mapObjectToBot(MapObject object) {
        return new Bot.BotBuilder()
                .source(object)
                .x(object.getCoordinates().x)
                .y(object.getCoordinates().y)
                .destX(object.getCoordinates().x)
                .destY(object.getCoordinates().y)
                .orientation(degreesToOrientation(object.getRotation()))
                .build();
    }

    private Instruction actionToInstruction(Action action) {
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

    @Override
    public Map<MapObject, Instruction> generate() {
        Map<MapObject, Instruction> instructions = new HashMap<>();
        notRecommendingAI.recommend(gameStateFromLevel(this.level)).forEach(recommendation -> instructions.put(
                (MapObject) recommendation.getActor().getSource(),
                actionToInstruction(recommendation.getAction()))
        );
        return instructions;
    }

}
