package ru.mipt.bit.platformer.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class MapObject implements Disposable {
    private final Texture texture;
    private final TextureRegion graphics;
    private final Rectangle rectangle;
    private GridPoint2 currentCoordinates;
    private GridPoint2 nextCoordinates;
    private float rotation;

    public MapObject(String textureFileName, int x, int y, float rotation) {
        this.texture = new Texture(textureFileName);
        this.graphics = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(graphics);
        this.currentCoordinates = new GridPoint2(x, y);
        this.nextCoordinates = new GridPoint2(x, y);
        this.rotation = rotation;
    }

    public void dispose() {
        texture.dispose();
    }

    public TextureRegion getGraphics() {
        return graphics;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public GridPoint2 getCurrentCoordinates() {
        return currentCoordinates;
    }

    public GridPoint2 getNextCoordinates() {
        return nextCoordinates;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public void setCurrentCoordinates(GridPoint2 currentCoordinates) {
        this.currentCoordinates = currentCoordinates;
    }

    public void setNextCoordinates(GridPoint2 nextCoordinates) {
        this.nextCoordinates = nextCoordinates;
    }

}
