package ru.mipt.bit.platformer.game.graphics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

public interface Graphics extends Disposable {
    void setGraphics(TiledMapTileLayer groundLayer);
    void drawTexture(SpriteBatch batch);
    void moveRectangles(TileMovement tileMovement);
    MapObject getObject();
}
