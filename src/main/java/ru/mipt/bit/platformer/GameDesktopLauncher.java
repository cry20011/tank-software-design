package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import ru.mipt.bit.platformer.game.Level;
import ru.mipt.bit.platformer.game.LevelGenerator;
import ru.mipt.bit.platformer.game.action_generators.BulletsController;
import ru.mipt.bit.platformer.game.action_generators.RandomTanksController;
import ru.mipt.bit.platformer.game.game_engine.CollisionDetector;
import ru.mipt.bit.platformer.game.graphics.GraphicsLevel;
import ru.mipt.bit.platformer.game.graphics.graphics_actions_generators.KeyboardTankGraphicsController;
import ru.mipt.bit.platformer.game.level_generators.FileLevelGenerator;

import java.util.List;

public class GameDesktopLauncher implements ApplicationListener {
    private final int width = 10;
    private final int height = 8;
    private final int maxTanksCount = 5;

    private GraphicsLevel graphicsLevel;
    private final CollisionDetector collisionDetector = new CollisionDetector(width, height);
    private Level level;

    @Override
    public void create() {
        graphicsLevel = new GraphicsLevel("level.tmx", List.of(new KeyboardTankGraphicsController()));

        LevelGenerator generator = new FileLevelGenerator(
                "src/main/resources/levels/level1.txt",
                List.of(new RandomTanksController(), new BulletsController()),
                List.of(graphicsLevel, collisionDetector)
        );
        level = generator.generate();
    }

    @Override
    public void render() {
        level.applyActions();
        graphicsLevel.moveRectangles();

        level.updateState(Gdx.graphics.getDeltaTime());
        graphicsLevel.renderGame();
    }

    @Override
    public void dispose() {
        graphicsLevel.dispose();
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
