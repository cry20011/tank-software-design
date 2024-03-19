package ru.mipt.bit.platformer.game.states;

import ru.mipt.bit.platformer.game.entities.Tank;

public class HardState extends TankState {
    private final float HARD_STATE_TANK_SPEED = 1.2f;

    public HardState(Tank tank) {
        super(tank);
        tank.updateState(this);
        tank.setMovementSpeed(HARD_STATE_TANK_SPEED);
    }
}
