package ru.mipt.bit.platformer.game.action_generators;


import org.awesome.ai.AI;
import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.ObjectsController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.mipt.bit.platformer.game.action_generators.ai_adapter_util.AiGameEntitiesConverter.convertAiAction;
import static ru.mipt.bit.platformer.game.action_generators.ai_adapter_util.AiGameEntitiesConverter.gameStateFromLevel;

public class AIMapObjectController implements ObjectsController, LevelListener {
    private final int width;
    private final int height;
    private final AI ai;

    private final List<MapObject> objects = new ArrayList<>();
    private final Map<MapObject, Action> objectActionMap = new HashMap<>();

    public AIMapObjectController(AI ai, int width, int height) {
        this.ai = ai;
        this.width = width;
        this.height = height;
    }

    @Override
    public void add(MapObject object) {
        objects.add(object);
    }

    @Override
    public void remove(MapObject object) {
        objects.remove(object);
    }

    @Override
    public Map<MapObject, Action> nextActions() {
        return ai.recommend(gameStateFromLevel(objects, width, height)).stream().collect(Collectors.toMap(
                recommendation -> (MapObject) recommendation.getActor().getSource(),
                recommendation -> convertAiAction(recommendation.getAction()))
        );
    }
}
