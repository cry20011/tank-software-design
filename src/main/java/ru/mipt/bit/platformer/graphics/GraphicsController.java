package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.entities.Movable;
import ru.mipt.bit.platformer.entities.Tank;
import ru.mipt.bit.platformer.entities.Tree;
import ru.mipt.bit.platformer.graphics.Graphics;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GraphicsController implements Disposable {
    private final SpriteBatch batch;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;

    private final Map<Class<? extends MapObject>, String> objectTexturesPathMap = new HashMap<>();
    private final Map<MapObject, Graphics> objectToGraphicsMap = new HashMap<>();

    public GraphicsController(String mapFile) {
        this.batch = new SpriteBatch();
        this.level = new TmxMapLoader().load(mapFile);
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = getSingleLayer(level);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        this.objectTexturesPathMap.put(Tank.class, "images/tank_blue.png");
        this.objectTexturesPathMap.put(Tree.class, "images/greenTree.png");
    }

    public void addGraphicsOf(MapObject object) {
        objectToGraphicsMap.put(object, new Graphics(objectTexturesPathMap.get(object.getClass())));
    }

    public void drawObject(Batch batch, MapObject object) {
        drawTextureRegionUnscaled(batch, objectToGraphicsMap.get(object).getRegion(), objectToGraphicsMap.get(object).getRectangle(), object.getRotation());
    }

    public void createObjects() {
        for (Map.Entry<MapObject, Graphics> entry: objectToGraphicsMap.entrySet()) {
            moveRectangleAtTileCenter(groundLayer, entry.getValue().getRectangle(), entry.getKey().getCoordinates());
        }
    }

    @Override
    public void dispose() {
        objectToGraphicsMap.values().forEach(Graphics::dispose);
        level.dispose();
        batch.dispose();
    }

    public void renderGame() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        levelRenderer.render();

        batch.begin();

        for (Map.Entry<MapObject, Graphics> entry: objectToGraphicsMap.entrySet()) {
            drawTextureRegionUnscaled(batch, entry.getValue().getRegion(), entry.getValue().getRectangle(), entry.getKey().getRotation());
        }

        batch.end();
    }

    public void moveRectangles() {
        for (MapObject object : objectToGraphicsMap.keySet()) {
            if (object instanceof Movable) {
                tileMovement.moveRectangleBetweenTileCenters(
                        objectToGraphicsMap.get(object).getRectangle(),
                        object.getCoordinates(),
                        ((Movable) object).getDestinationCoordinates(),
                        ((Movable) object).getMovementProgress()
                );
            }
        }
    }
}
