package ru.mipt.bit.platformer.game.actions;

import ru.mipt.bit.platformer.game.Action;
import ru.mipt.bit.platformer.game.MapObject;
import ru.mipt.bit.platformer.game.entities.Shootable;

public class Shoot implements Action {
    @Override
    public void applyTo(MapObject object) {
        ((Shootable) object).shoot();
    }
}
