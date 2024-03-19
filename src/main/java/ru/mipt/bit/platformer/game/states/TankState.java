package ru.mipt.bit.platformer.game.states;

import ru.mipt.bit.platformer.game.entities.Tank;

public abstract class TankState {
    private final Tank tank;
    private final TankStateDecider tankStateDecider = new TankStateDecider();


    protected TankState(Tank tank) {
        this.tank = tank;
    }

    public Tank getTank() {
        return tank;
    }

    public void onHpChange() {
        tankStateDecider.checkTankState(tank);
    };
}
