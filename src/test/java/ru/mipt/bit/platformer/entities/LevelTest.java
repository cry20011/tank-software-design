//package ru.mipt.bit.platformer.entities;
//
//import com.badlogic.gdx.math.GridPoint2;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import ru.mipt.bit.platformer.common.Level;
//import ru.mipt.bit.platformer.common.ObjectAddHandler;
//import ru.mipt.bit.platformer.controllers.KeyboadActionGenerator;
//import ru.mipt.bit.platformer.instructions.Direction;
//import ru.mipt.bit.platformer.instructions.Action;
//
//import java.util.*;
//
//class LevelTest {
//
//    @Test
//    void TestLevel_add() {
//        Tank player = new Tank(new GridPoint2(0, 0), Direction.RIGHT, 1f);
//
//        KeyboadActionGenerator inputController = Mockito.mock(KeyboadActionGenerator.class);
//        ObjectAddHandler handler1 = Mockito.mock(ObjectAddHandler.class);
//        ObjectAddHandler handler2 = Mockito.mock(ObjectAddHandler.class);
//
//        Level level = new Level(player, inputController, List.of(handler1, handler2));
//        level.add(Mockito.mock(Tank.class));
//        level.add(Mockito.mock(Tree.class));
//
//        Mockito.verify(handler1, Mockito.times(3)).add(Mockito.any());
//        Mockito.verify(handler2, Mockito.times(3)).add(Mockito.any());
//    }
//
//    @Test
//    void TestLevel_applyInstructions() {
//        Tank player = new Tank(new GridPoint2(0, 0), Direction.RIGHT, 1f);
//
//        Action action = Mockito.mock(Action.class);
//        Map<MapObject, Action> instructions = new HashMap<>();
//        instructions.put(player, action);
//
//        KeyboadActionGenerator inputController = Mockito.mock(KeyboadActionGenerator.class);
//        Mockito.when(inputController.getInstructions()).thenReturn(instructions);
//
//        Level level = new Level(player, inputController, Collections.emptyList());
//
//        level.applyInstructions();
//
//        Mockito.verify(action, Mockito.times(1)).apply(player);
//    }
//
//    @Test
//    void TestLevel_updateState() {
//        Tank player = Mockito.mock(Tank.class);
//        Tank tank = Mockito.mock(Tank.class);
//        Tree tree = Mockito.mock(Tree.class);
//        KeyboadActionGenerator inputController = Mockito.mock(KeyboadActionGenerator.class);
//
//        Level level = new Level(player, inputController, Collections.emptyList());
//        level.add(tank);
//        level.add(tree);
//
//        float deltaTime = 1f;
//        level.updateState(deltaTime);
//
//        Mockito.verify(player, Mockito.times(1)).updateState(deltaTime);
//        Mockito.verify(tank, Mockito.times(1)).updateState(deltaTime);
//        Mockito.verify(tree, Mockito.times(1)).updateState(deltaTime);
//    }
//}