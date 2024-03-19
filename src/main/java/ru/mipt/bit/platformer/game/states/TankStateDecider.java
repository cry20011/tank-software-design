package ru.mipt.bit.platformer.game.states;

import ru.mipt.bit.platformer.game.entities.Tank;

import java.lang.reflect.InvocationTargetException;
import java.util.SortedMap;
import java.util.TreeMap;

public class TankStateDecider {
    private final SortedMap<Integer, Class<? extends TankState>> hpUpperBounderToState = new TreeMap<>();

    public TankStateDecider() {
        hpUpperBounderToState.put(100, LightState.class);
        hpUpperBounderToState.put(70, MiddleState.class);
        hpUpperBounderToState.put(15, HardState.class);
    }

    public void checkTankState(Tank tank) {
        for (int hpUpperBounder : hpUpperBounderToState.keySet()) {
            if (tank.getHealthPercent() <= hpUpperBounder) {
                if (stateNotChanged(tank, hpUpperBounder)) {
                    return;
                } else {
                    try {
                        hpUpperBounderToState.get(hpUpperBounder).getConstructor(Tank.class).newInstance(tank);
                        return;
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                             NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private boolean stateNotChanged(Tank tank, int hpUpperBounder) {
        return tank.getState().getClass() == hpUpperBounderToState.get(hpUpperBounder);
    }
}
