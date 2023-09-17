package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.Instruction;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class Map implements Disposable {
    private final Batch batch;
    private final TiledMap tiledMap;
    private final MapRenderer renderer;
    private final TiledMapTileLayer groundLayer;
    private final TileMovement tileMovement;

    private final Tank tank;
    private final List<Obstacle> obtacles;

    public Map(String mapFileName, Interpolation interpolation, Tank tank, Obstacle... obstacles) {
        this.batch = new SpriteBatch();
        this.tiledMap = new TmxMapLoader().load(mapFileName);
        this.renderer = createSingleLayerMapRenderer(tiledMap, batch);
        this.groundLayer = getSingleLayer(tiledMap);
        this.tileMovement = new TileMovement(this.groundLayer, interpolation);
        this.obtacles = List.of(obstacles);

        this.tank = tank;
        for (MapObject tree: obstacles) {
            moveRectangleAtTileCenter(groundLayer, tree.getRectangle(), tree.getCurrentCoordinates());
        }
    }

    public void render(Instruction instruction) {
        tank.move(tileMovement, obtacles, Gdx.graphics.getDeltaTime(), instruction);

        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        renderer.render();
        batch.begin();

        drawTextureRegionUnscaled(batch, tank);
        for (MapObject obtacle: obtacles) {
            drawTextureRegionUnscaled(batch, obtacle);
        }

        batch.end();
    }

    public void dispose() {
        tank.dispose();
        for (MapObject obtacle: obtacles) {
            obtacle.dispose();
        }
        tiledMap.dispose();
        batch.dispose();
    }
}
