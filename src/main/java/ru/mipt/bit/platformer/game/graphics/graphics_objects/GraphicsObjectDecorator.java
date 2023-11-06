package ru.mipt.bit.platformer.game.graphics.graphics_objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.graphics.Graphics;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

abstract class GraphicsObjectDecorator implements Graphics {
    protected Graphics mapObjectGraphics;

    public GraphicsObjectDecorator(Graphics mapObjectGraphics) {
        this.mapObjectGraphics = mapObjectGraphics;
    }

    @Override
    public void setGraphics(TiledMapTileLayer groundLayer) {
        mapObjectGraphics.setGraphics(groundLayer);
    }

    @Override
    public void drawTexture(SpriteBatch batch) {
        mapObjectGraphics.drawTexture(batch);
    }

    @Override
    public void moveRectangles(TileMovement tileMovement) {
        mapObjectGraphics.moveRectangles(tileMovement);
    }

    @Override
    public MapObject getObject() {
        return mapObjectGraphics.getObject();
    }

    @Override
    public void dispose() {
        mapObjectGraphics.dispose();
    }

}
