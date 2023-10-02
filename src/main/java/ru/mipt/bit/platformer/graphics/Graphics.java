package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;

public class Graphics implements Disposable {
    private final Texture texture;
    private final TextureRegion region;
    private final Rectangle rectangle;

    public Graphics(String texturePicPath) {
        this.texture = new Texture(texturePicPath);
        this.region = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(region);
    }

    public TextureRegion getRegion() {
        return region;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
