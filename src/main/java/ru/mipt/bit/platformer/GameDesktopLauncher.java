package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.common.Level;
import ru.mipt.bit.platformer.controllers.CollisionDetector;
import ru.mipt.bit.platformer.controllers.GraphicsController;
import ru.mipt.bit.platformer.controllers.InputController;
import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.generators.FileLevelGenerator;
import ru.mipt.bit.platformer.generators.LevelGenerator;
import ru.mipt.bit.platformer.generators.RandomLevelGenerator;
import ru.mipt.bit.platformer.instructions.Direction;

import java.util.List;

public class GameDesktopLauncher implements ApplicationListener {
    private static final float DEFAULT_MOVEMENT_SPEED = 0.4f;
    private GraphicsController graphicsController;
    private final CollisionDetector collisionDetector = new CollisionDetector();
    private Level level;

    @Override
    public void create() {
        InputController inputController = new InputController();
        graphicsController = new GraphicsController("level.tmx");

//        LevelGenerator generator = new FileLevelGenerator("src/main/resources/levels/level1.txt", inputController, List.of(graphicsController, collisionDetector));
        LevelGenerator generator = new RandomLevelGenerator(8, 8, inputController, List.of(graphicsController, collisionDetector));
        level = generator.generate();



        initMappings(inputController, level.getPlayer());
        graphicsController.createObjects();
    }

    void initMappings(InputController inputController, MapObject player) {
        inputController.addMapping(Input.Keys.UP, Direction.UP, player);
        inputController.addMapping(Input.Keys.W, Direction.UP, player);
        inputController.addMapping(Input.Keys.LEFT, Direction.LEFT, player);
        inputController.addMapping(Input.Keys.A, Direction.LEFT, player);
        inputController.addMapping(Input.Keys.DOWN, Direction.DOWN, player);
        inputController.addMapping(Input.Keys.S, Direction.DOWN, player);
        inputController.addMapping(Input.Keys.RIGHT, Direction.RIGHT, player);
        inputController.addMapping(Input.Keys.D, Direction.RIGHT, player);
    }


    @Override
    public void render() {
        level.applyInstructions();
        graphicsController.moveRectangles();

        level.updateState(Gdx.graphics.getDeltaTime());
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
