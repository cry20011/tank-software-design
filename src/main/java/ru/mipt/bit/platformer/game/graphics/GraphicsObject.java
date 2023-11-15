package ru.mipt.bit.platformer.game.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.graphics.util.TileMovement;

public interface GraphicsObject extends Disposable {
    void setupGraphics(TiledMapTileLayer groundLayer);
    void drawTexture(SpriteBatch batch);
    void moveGraphicsRectangle(TileMovement tileMovement);
    GameObject getObject();
}
