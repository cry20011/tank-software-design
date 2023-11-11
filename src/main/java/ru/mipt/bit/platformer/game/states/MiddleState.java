package ru.mipt.bit.platformer.game.states;

import ru.mipt.bit.platformer.game.entities.Tank;

public class MiddleState extends TankState {
    private final float MIDDLE_STATE_TANK_SPEED = 0.8f;

    public MiddleState(Tank tank) {
        super(tank);
        tank.updateState(this);
        tank.setMovementSpeed(MIDDLE_STATE_TANK_SPEED);
    }
}
