package ru.mipt.bit.platformer.game.action_generators;


import org.awesome.ai.AI;
import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.ObjectsController;
import ru.mipt.bit.platformer.game.entities.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.mipt.bit.platformer.game.action_generators.ai_adapter_util.AiGameEntitiesConverter.convertAiAction;
import static ru.mipt.bit.platformer.game.action_generators.ai_adapter_util.AiGameEntitiesConverter.makeGameState;

public class AIMapObjectController implements ObjectsController, LevelListener {
    private final int width;
    private final int height;
    private final AI ai;
    private final List<GameObject> objects = new ArrayList<>();

    public AIMapObjectController(AI ai, int width, int height) {
        this.ai = ai;
        this.width = width;
        this.height = height;
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

    @Override
    public Map<GameObject, Action> nextActions() {
        return ai.recommend(makeGameState(objects, width, height)).stream().collect(Collectors.toMap(
                recommendation -> (GameObject) recommendation.getActor().getSource(),
                recommendation -> convertAiAction(recommendation.getAction()))
        );
    }
}
