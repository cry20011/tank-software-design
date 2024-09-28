package ru.mipt.bit.platformer.game.graphics;

import java.util.Map;

public interface GraphicsController extends GraphicsListener {
    Map<GraphicsObject, GraphicsAction> nextActions();
}
