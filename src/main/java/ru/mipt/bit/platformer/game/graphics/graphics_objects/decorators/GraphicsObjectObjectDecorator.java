package ru.mipt.bit.platformer.game.graphics.graphics_objects.decorators;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.graphics.GraphicsObject;
import ru.mipt.bit.platformer.util.TileMovement;

abstract class GraphicsObjectObjectDecorator implements GraphicsObject {
    protected GraphicsObject mapObjectGraphicsObject;

    public GraphicsObjectObjectDecorator(GraphicsObject mapObjectGraphicsObject) {
        this.mapObjectGraphicsObject = mapObjectGraphicsObject;
    }

    @Override
    public void setupGraphics(TiledMapTileLayer groundLayer) {
        mapObjectGraphicsObject.setupGraphics(groundLayer);
    }

    @Override
    public void drawTexture(SpriteBatch batch) {
        mapObjectGraphicsObject.drawTexture(batch);
    }

    @Override
    public void moveGraphicsRectangle(TileMovement tileMovement) {
        mapObjectGraphicsObject.moveGraphicsRectangle(tileMovement);
    }

    @Override
    public MapObject getObject() {
        return mapObjectGraphicsObject.getObject();
    }

    @Override
    public void dispose() {
        mapObjectGraphicsObject.dispose();
    }

}
