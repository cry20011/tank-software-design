package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.entities.Map;
import ru.mipt.bit.platformer.entities.Obstacle;
import ru.mipt.bit.platformer.entities.Tank;
import ru.mipt.bit.platformer.util.Instruction;

import static com.badlogic.gdx.Input.Keys.*;
import static ru.mipt.bit.platformer.util.Instruction.*;

public class GameDesktopLauncher implements ApplicationListener {
    private Map map;

    @Override
    public void create() {
        Tank tank = new Tank("images/tank_blue.png", 1, 1, 0f, 1f, 0.4f);
        Obstacle obstacle = new Obstacle("images/greenTree.png", 1, 3);

        map = new Map("level.tmx", Interpolation.smooth, tank ,obstacle);
    }

    private Instruction getInstruction() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return GO_UP;
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return GO_LEFT;
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return GO_DOWN;
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return GO_RIGHT;
        }
        return null;
    }

    @Override
    public void render() {
        map.render(getInstruction());
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

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        map.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
