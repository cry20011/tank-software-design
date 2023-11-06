package ru.mipt.bit.platformer.game.graphics.graphics_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.actions.Direction;
import ru.mipt.bit.platformer.game.graphics.Graphics;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class HealthDecorator extends GraphicsObjectDecorator {
    private final Texture texture;
    private final TextureRegion region;
    private final Rectangle rectangle;

    public HealthDecorator(Graphics mapObjectGraphics) {
        super(mapObjectGraphics);
        this.texture = new Texture("images/bullet.png");
        this.region = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(region);
    }

    @Override
    public void setGraphics(TiledMapTileLayer groundLayer) {
        moveRectangleAtTileCenter(groundLayer, rectangle, super.getObject().getCoordinates().cpy().sub(0, 1));
        super.setGraphics(groundLayer);
    }

    @Override
    public void drawTexture(SpriteBatch batch) {
        drawTextureRegionUnscaled(batch, region, rectangle, Direction.UP.getRotation());
        super.drawTexture(batch);
    }

    @Override
    public void moveRectangles(TileMovement tileMovement) {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, getObject().getCoordinates().cpy().sub(0, 1), getObject().getDestinationCoordinates().cpy().sub(0, 1), getObject().getMovementProgress());
        super.moveRectangles(tileMovement);
    }

    @Override
    public MapObject getObject() {
        return super.getObject();
    }

    @Override
    public void dispose() {
        texture.dispose();
        super.dispose();
    }
}
