package ru.mipt.bit.platformer.game.action_generators;


import org.awesome.ai.AI;
import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.ActionGenerator;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.ObjectAddHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.mipt.bit.platformer.game.action_generators.ai_adapter_util.AiGameEntitiesConverter.convertAiAction;
import static ru.mipt.bit.platformer.game.action_generators.ai_adapter_util.AiGameEntitiesConverter.gameStateFromLevel;

public class AIInstructionsGenerator implements ActionGenerator, ObjectAddHandler {
    private final int width;
    private final int height;
    private final AI ai;

    private final List<MapObject> objects = new ArrayList<>();
    private final Map<MapObject, Action> objectActionMap = new HashMap<>();

    public AIInstructionsGenerator(AI ai, int width, int height) {
        this.ai = ai;
        this.width = width;
        this.height = height;
    }

    @Override
    public Action generateFor(MapObject object) {
        if (objectActionMap.isEmpty()) {
            updateActions();
        }

        Action action = objectActionMap.get(object);
        objectActionMap.remove(object);
        return action;
    }

    private void updateActions() {
        ai.recommend(gameStateFromLevel(objects, width, height)).forEach(
                recommendation -> objectActionMap.put(
                        (MapObject) recommendation.getActor().getSource(),
                        convertAiAction(recommendation.getAction())
                )
        );
    }

    @Override
    public void add(MapObject object) {
        objects.add(object);
    }
}
