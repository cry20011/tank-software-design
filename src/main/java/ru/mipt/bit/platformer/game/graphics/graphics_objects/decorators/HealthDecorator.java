package ru.mipt.bit.platformer.game.graphics.graphics_objects.decorators;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.entities.Damagable;
import ru.mipt.bit.platformer.game.graphics.GraphicsObject;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class HealthDecorator extends GraphicsObjectObjectDecorator {
    private final TextureRegion hpBarRegion;
    private final Rectangle hpBarRectangle;
    private TextureRegion leftHpRegion;
    private final Rectangle leftHpRectangle;


    public HealthDecorator(GraphicsObject mapObjectGraphicsObject) {
        super(mapObjectGraphicsObject);
        this.hpBarRegion = new TextureRegion(createHpBarTexture(100, Color.WHITE));
        this.hpBarRectangle = createBoundingRectangle(this.hpBarRegion);
        this.leftHpRegion = new TextureRegion(createHpBarTexture(100, Color.GREEN));
        this.leftHpRectangle = createBoundingRectangle(this.leftHpRegion);
    }

    private Texture createHpBarTexture(int health, Color color) {
        Pixmap pixmap = new Pixmap(90 * health / 100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0, 0, 90 * health / 100, 20);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    @Override
    public void setupGraphics(TiledMapTileLayer groundLayer) {
        moveRectangleAtTileCenter(groundLayer, hpBarRectangle, decrementedY(getObject().getCoordinates()));
        moveRectangleAtTileCenter(groundLayer, leftHpRectangle, decrementedY(getObject().getCoordinates()));
        super.setupGraphics(groundLayer);
    }

    @Override
    public void drawTexture(SpriteBatch batch) {
        super.drawTexture(batch);
    }

    public void drawHpBar(SpriteBatch batch) {
        updateCurrentHp();

        drawTextureRegionUnscaled(batch, hpBarRegion, hpBarRectangle, 0);
        drawTextureRegionUnscaled(batch, leftHpRegion, leftHpRectangle, 0);
    }

    private void updateCurrentHp() {
        this.leftHpRegion = new TextureRegion(createHpBarTexture(((Damagable)getObject()).getHealthPercent(), Color.GREEN));
    }

    @Override
    public void moveGraphicsRectangle(TileMovement tileMovement) {
        tileMovement.moveRectangleBetweenTileCenters(
                hpBarRectangle,
                decrementedY(getObject().getCoordinates()),
                decrementedY(getObject().getDestinationCoordinates()),
                getObject().getMovementProgress()
        );
        tileMovement.moveRectangleBetweenTileCenters(
                leftHpRectangle,
                decrementedY(getObject().getCoordinates()),
                decrementedY(getObject().getDestinationCoordinates()),
                getObject().getMovementProgress()
        );
        super.moveGraphicsRectangle(tileMovement);
    }

    @Override
    public MapObject getObject() {
        return super.getObject();
    }

    @Override
    public void dispose() {
        hpBarRegion.getTexture().dispose();
        leftHpRegion.getTexture().dispose();
        super.dispose();
    }
}
