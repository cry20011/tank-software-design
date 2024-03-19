package ru.mipt.bit.platformer.game.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GraphicsAction {
    void apply(SpriteBatch batch, GraphicsObject graphicsObject);
}
