package ru.mipt.bit.platformer.game.graphics.graphics_objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.graphics.GraphicsObject;
import ru.mipt.bit.platformer.game.graphics.util.TileMovement;

import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.*;

public class GameObjectGraphics implements GraphicsObject {
    private final Texture texture;
    private final TextureRegion region;
    private final Rectangle rectangle;
    private final GameObject object;

    public GameObjectGraphics(String texturePicPath, GameObject object) {
        this.texture = new Texture(texturePicPath);
        this.region = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(region);
        this.object = object;
    }

    public GameObject getObject() {
        return object;
    }

    public void setupGraphics(TiledMapTileLayer groundLayer) {
        moveRectangleAtTileCenter(groundLayer, rectangle, object.getCoordinates());
    }

    @Override
    public void drawTexture(SpriteBatch batch) {
        drawTextureRegionUnscaled(batch, region, rectangle, object.getDirection().getRotation());
    }

    @Override
    public void moveGraphicsRectangle(TileMovement tileMovement) {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, object.getCoordinates(), object.getDestinationCoordinates(), object.getMovementProgress());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
