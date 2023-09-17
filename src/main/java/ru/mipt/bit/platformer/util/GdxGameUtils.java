package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ru.mipt.bit.platformer.entities.MapObject;

import java.util.NoSuchElementException;

import static com.badlogic.gdx.math.MathUtils.clamp;

public final class GdxGameUtils {

    private GdxGameUtils() {
    }

    public static MapRenderer createSingleLayerMapRenderer(TiledMap tiledMap, Batch batch) {
        TiledMapTileLayer tileLayer = getSingleLayer(tiledMap);
        float viewWidth = tileLayer.getWidth() * tileLayer.getTileWidth();
        float viewHeight = tileLayer.getHeight() * tileLayer.getTileHeight();

        OrthogonalTiledMapRenderer mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);
        mapRenderer.getViewBounds().set(0f, 0f, viewWidth, viewHeight);

        return mapRenderer;
    }

    public static <L extends MapLayer> L getSingleLayer(Map map) {
        MapLayers layers = map.getLayers();
        switch (layers.size()) {
            case 0:
                throw new NoSuchElementException("Map has no layers");
            case 1:
                @SuppressWarnings("unchecked")
                L layer = (L) layers.iterator().next();
                return layer;
            default:
                throw new IllegalArgumentException("Map has more than one layer");
        }
    }

    public static Rectangle moveRectangleAtTileCenter(TiledMapTileLayer tileLayer, Rectangle rectangle, GridPoint2 tileCoordinates) {
        Vector2 tileCenter = calculateTileCenter(tileLayer, tileCoordinates);
        return rectangle.setCenter(tileCenter);
    }

    public static GridPoint2 changedX(GridPoint2 point, int diff) {
        return new GridPoint2(point).add(diff, 0);
    }

    public static GridPoint2 changedY(GridPoint2 point, int diff) {
        return new GridPoint2(point).add(0, diff);
    }

    public static GridPoint2 incrementedY(GridPoint2 point) {
        return new GridPoint2(point).add(0, 1);
    }

    public static GridPoint2 decrementedX(GridPoint2 point) {
        return new GridPoint2(point).sub(1, 0);
    }

    public static GridPoint2 decrementedY(GridPoint2 point) {
        return new GridPoint2(point).sub(0, 1);
    }

    public static GridPoint2 incrementedX(GridPoint2 point) {
        return new GridPoint2(point).add(1, 0);
    }

    public static void drawTextureRegionUnscaled(Batch batch, MapObject object) {
        int regionWidth = object.getGraphics().getRegionWidth();
        int regionHeight = object.getGraphics().getRegionHeight();
        float regionOriginX = regionWidth / 2f;
        float regionOriginY = regionHeight / 2f;
        Rectangle rectangle = object.getRectangle();
        batch.draw(object.getGraphics(), rectangle.x, rectangle.y, regionOriginX, regionOriginY, regionWidth, regionHeight, 1f, 1f, object.getRotation());
    }

    public static Rectangle createBoundingRectangle(TextureRegion region) {
        return new Rectangle()
                .setWidth(region.getRegionWidth())
                .setHeight(region.getRegionHeight());
    }

    public static float continueProgress(float previousProgress, float deltaTime, float speed) {
        return clamp(previousProgress + deltaTime / speed, 0f, 1f);
    }

    private static Vector2 calculateTileCenter(TiledMapTileLayer tileLayer, GridPoint2 tileCoordinates) {
        int tileWidth = tileLayer.getTileWidth();
        int tileHeight = tileLayer.getTileHeight();
        int tileBottomLeftCornerX = tileCoordinates.x * tileWidth;
        int tileBottomLeftCornerY = tileCoordinates.y * tileHeight;

        return new Rectangle()
                .setX(tileBottomLeftCornerX)
                .setY(tileBottomLeftCornerY)
                .setWidth(tileWidth)
                .setHeight(tileHeight)
                .getCenter(new Vector2());
    }
}
