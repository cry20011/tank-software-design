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
import ru.mipt.bit.platformer.game.entities.Damagable;
import ru.mipt.bit.platformer.game.entities.Tank;
import ru.mipt.bit.platformer.game.entities.Tree;
import ru.mipt.bit.platformer.game.graphics.graphics_objects.decorators.HealthDecorator;
import ru.mipt.bit.platformer.game.graphics.graphics_objects.MapObjectGraphicsObject;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class GraphicsLevel implements Disposable, LevelListener {
    private final SpriteBatch batch;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;

    private final Map<Class<? extends MapObject>, String> objectTexturesPathMap = new HashMap<>();
    private final Map<MapObject, GraphicsObject> objectToGraphicsMap = new HashMap<>();
    private final List<GraphicsController> graphicsControllers = new ArrayList<>();

    public GraphicsLevel(String mapFile, List<GraphicsController> actionGenerators) {
        this.batch = new SpriteBatch();
        this.level = new TmxMapLoader().load(mapFile);
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = getSingleLayer(level);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        this.objectTexturesPathMap.put(Tank.class, "images/tank_blue.png");
        this.objectTexturesPathMap.put(Tree.class, "images/greenTree.png");
        this.objectTexturesPathMap.put(Bullet.class, "images/bullet.png");

        this.graphicsControllers.addAll(actionGenerators);
    }

    private GraphicsObject makeGraphics(MapObject object) {
        GraphicsObject graphicsObject = new MapObjectGraphicsObject(objectTexturesPathMap.get(object.getClass()), object);
        if (object instanceof Damagable) {
            graphicsObject = new HealthDecorator(graphicsObject);
        }
        return graphicsObject;
    }

    @Override
    public void add(MapObject object) {
        GraphicsObject graphicsObject = makeGraphics(object);
        graphicsObject.setupGraphics(groundLayer);
        objectToGraphicsMap.put(object, graphicsObject);
        graphicsControllers.forEach(graphicsController -> graphicsController.add(graphicsObject));
    }

    @Override
    public void remove(MapObject object) {
        graphicsControllers.forEach(generator -> generator.remove(objectToGraphicsMap.get(object)));
        objectToGraphicsMap.remove(object);
    }

    public void renderGame() {
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        levelRenderer.render();

        batch.begin();

        graphicsControllers.forEach(generator -> generator.nextActions().forEach((graphics, graphicsAction) -> graphicsAction.apply(batch, graphics)));
        objectToGraphicsMap.values().forEach(graphics -> graphics.drawTexture(batch));

        batch.end();
    }

    public void moveRectangles() {
        objectToGraphicsMap.values().forEach(graphics -> graphics.moveGraphicsRectangle(tileMovement));
    }

    @Override
    public void dispose() {
        objectToGraphicsMap.values().forEach(Disposable::dispose);
        level.dispose();
        batch.dispose();
    }
}
