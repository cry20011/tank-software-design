package ru.mipt.bit.platformer.controllers;


import org.awesome.ai.AI;
import org.awesome.ai.Action;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.state.movable.Player;
import org.awesome.ai.strategy.NotRecommendingAI;
import ru.mipt.bit.platformer.common.Level;
import ru.mipt.bit.platformer.common.ObjectAddHandler;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.instructions.Direction;
import ru.mipt.bit.platformer.instructions.Instruction;;import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AIInstructionsGenerator implements InstructionsGenerator, ObjectAddHandler {
    private final List<MapObject> objects = new ArrayList<>();
    private final int width;
    private final int height;

    private final AI ai = new NotRecommendingAI();

    public AIInstructionsGenerator(int width, int height) {
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

    private GameState makeGameState() {
        return new GameState.GameStateBuilder()
                .bots(objectsToBots(objects))
                .levelWidth(width)
                .levelHeight(height)
                .build();
    }

    private List<Bot> objectsToBots(List<MapObject> objects) {
        List<Bot> bots = new ArrayList<>();
        objects.forEach(object -> bots.add(objectToBot(object)));
        return bots;
    }

    private Bot objectToBot(MapObject object) {
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
        ai.recommend(makeGameState()).forEach(recommendation -> instructions.put(
                (MapObject) recommendation.getActor().getSource(),
                actionToInstruction(recommendation.getAction()))
        );
        return instructions;
    }

    @Override
    public void add(MapObject object) {
        objects.add(object);
    }
}
