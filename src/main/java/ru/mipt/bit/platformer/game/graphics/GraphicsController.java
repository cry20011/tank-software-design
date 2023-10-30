package ru.mipt.bit.platformer.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.game.entities.Bullet;
import ru.mipt.bit.platformer.game.entities.Tank;
import ru.mipt.bit.platformer.game.entities.Tree;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GraphicsController implements Disposable, LevelListener {
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
        this.objectTexturesPathMap.put(Bullet.class, "images/bullet.png");
    }

    @Override
    public void add(MapObject object) {
        objectToGraphicsMap.put(object, new Graphics(objectTexturesPathMap.get(object.getClass())));
        moveRectangleAtTileCenter(groundLayer, objectToGraphicsMap.get(object).getRectangle(), object.getCoordinates());
    }

    @Override
    public void remove(MapObject object) {
        objectToGraphicsMap.remove(object);
    }

    public void renderGame() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        levelRenderer.render();

        batch.begin();

        for (Map.Entry<MapObject, Graphics> entry: objectToGraphicsMap.entrySet()) {
            drawTextureRegionUnscaled(batch, entry.getValue().getRegion(), entry.getValue().getRectangle(), entry.getKey().getDirection().getRotation());
        }

        batch.end();
    }

    public void moveRectangles() {
        for (MapObject object : objectToGraphicsMap.keySet()) {
            tileMovement.moveRectangleBetweenTileCenters(
                    objectToGraphicsMap.get(object).getRectangle(),
                    object.getCoordinates(),
                    object.getDestinationCoordinates(),
                    object.getMovementProgress()
            );
        }
    }

    @Override
    public void dispose() {
        objectToGraphicsMap.values().forEach(Graphics::dispose);
        level.dispose();
        batch.dispose();
    }
}
