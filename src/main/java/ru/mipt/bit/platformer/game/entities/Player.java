package ru.mipt.bit.platformer.game.entities;

import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.game.Toggle;

public class Player extends Tank {
    private final Toggle healthToggle = new Toggle(true);

    public Player(GridPoint2 coordinates) {
        super(coordinates);
    }

    public Toggle getHealthToggle() {
        return healthToggle;
    }
}
