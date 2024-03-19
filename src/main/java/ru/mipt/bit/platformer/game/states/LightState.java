package ru.mipt.bit.platformer.game.states;

import ru.mipt.bit.platformer.game.entities.Tank;

public class LightState extends TankState {
    private final float LIGHT_STATE_TANK_SPEED = 0.4f;

    public LightState(Tank tank) {
        super(tank);
        tank.updateState(this);
        tank.setMovementSpeed(LIGHT_STATE_TANK_SPEED);

    }
}
