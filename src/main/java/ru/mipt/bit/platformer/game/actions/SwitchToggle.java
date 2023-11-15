package ru.mipt.bit.platformer.game.actions;

import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.GameObject;
import ru.mipt.bit.platformer.game.entities.Player;

public class SwitchToggle implements Action {
    @Override
    public void apply(GameObject object) {
        ((Player) object).getHealthToggle().switchToggle();
    }
}
