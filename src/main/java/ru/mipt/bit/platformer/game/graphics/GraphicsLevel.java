package ru.mipt.bit.platformer.game.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.LevelListener;
import ru.mipt.bit.platformer.game.entities.*;
import ru.mipt.bit.platformer.game.graphics.graphics_objects.GameObjectGraphics;
import ru.mipt.bit.platformer.game.graphics.graphics_objects.decorators.HealthDecorator;
import ru.mipt.bit.platformer.game.graphics.util.TileMovement;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.game.graphics.util.GdxGameUtils.getSingleLayer;

public class GraphicsLevel implements Disposable, LevelListener {
    private final SpriteBatch batch;
    private final TiledMap level;
    private final MapRenderer levelRenderer;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;

    private final Map<Class<? extends GameObject>, String> objectTexturesPathMap = new HashMap<>();
    private final Map<GameObject, GraphicsObject> objectToGraphicsMap = new HashMap<>();

    private Player player;

    public GraphicsLevel(String mapFile) {
        this.batch = new SpriteBatch();
        this.level = new TmxMapLoader().load(mapFile);
        this.levelRenderer = createSingleLayerMapRenderer(level, batch);
        this.groundLayer = getSingleLayer(level);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        this.objectTexturesPathMap.put(Tank.class, "images/tank_blue.png");
        this.objectTexturesPathMap.put(Tree.class, "images/greenTree.png");
        this.objectTexturesPathMap.put(Bullet.class, "images/bullet.png");
        this.objectTexturesPathMap.put(Player.class, "images/player_tank.png");
    }

    private GraphicsObject makeGraphics(GameObject object) {
        GraphicsObject graphicsObject = new GameObjectGraphics(objectTexturesPathMap.get(object.getClass()), object);
        if (object instanceof Damagable) {
            graphicsObject = new HealthDecorator(graphicsObject, player.getHealthToggle());
        }
        return graphicsObject;
    }

    @Override
    public void add(GameObject object) {
        GraphicsObject graphicsObject = makeGraphics(object);
        graphicsObject.setupGraphics(groundLayer);
        objectToGraphicsMap.put(object, graphicsObject);
    }

    @Override
    public void addPlayer(Player player) {
        this.player = player;
        GraphicsObject graphicsObject = makeGraphics(this.player);
        graphicsObject.setupGraphics(groundLayer);
        objectToGraphicsMap.put(this.player, graphicsObject);
    }

    @Override
    public void remove(GameObject object) {
        objectToGraphicsMap.remove(object);
    }

    public void renderGame() {
        moveRectangles();

        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        levelRenderer.render();

        batch.begin();

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
