package ru.mipt.bit.platformer.game.graphics.graphics_actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.mipt.bit.platformer.game.graphics.GraphicsObject;
import ru.mipt.bit.platformer.game.graphics.GraphicsAction;
import ru.mipt.bit.platformer.game.graphics.graphics_objects.decorators.HealthDecorator;

public class ShowHealth implements GraphicsAction {
    @Override
    public void apply(SpriteBatch batch, GraphicsObject graphicsObject) {
        if (graphicsObject instanceof HealthDecorator healthDecorator) {
            healthDecorator.drawHpBar(batch);
        }
    }
}
