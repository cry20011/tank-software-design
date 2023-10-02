package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.entities.Level;
import ru.mipt.bit.platformer.entities.Tank;
import ru.mipt.bit.platformer.entities.Tree;
import ru.mipt.bit.platformer.graphics.GraphicsController;
import ru.mipt.bit.platformer.instructions.Direction;
import ru.mipt.bit.platformer.instructions.InputController;

public class GameDesktopLauncher implements ApplicationListener {
    private static final float DEFAULT_MOVEMENT_SPEED = 0.4f;
    private GraphicsController graphicsController;
    private Level level;

    @Override
    public void create() {
        Tank player = new Tank(new GridPoint2(2, 1), Direction.RIGHT, DEFAULT_MOVEMENT_SPEED);
        InputController inputController = new InputController(player);

        graphicsController = new GraphicsController("level.tmx");
        level = new Level(player, graphicsController, inputController);

        level.add(new Tank(new GridPoint2(2, 4), Direction.UP, DEFAULT_MOVEMENT_SPEED));
        level.add(new Tank(new GridPoint2(1, 4), Direction.UP, DEFAULT_MOVEMENT_SPEED));
        level.add(new Tank(new GridPoint2(3, 4), Direction.UP, DEFAULT_MOVEMENT_SPEED));
        level.add(new Tree(new GridPoint2(1, 3)));
        level.add(new Tree(new GridPoint2(2, 3)));
        level.add(new Tree(new GridPoint2(3, 3)));
        level.add(new Tree(new GridPoint2(4, 4)));
        level.add(new Tree(new GridPoint2(0, 4)));

        graphicsController.createObjects();
    }

    @Override
    public void render() {
        level.moveObjects(Gdx.graphics.getDeltaTime());
        graphicsController.renderGame();
    }

    @Override
    public void dispose() {
        graphicsController.dispose();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
