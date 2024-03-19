package ru.mipt.bit.platformer.game;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Toggle {
    private final long CHANGE_TOGGLE_RELOAD_MILLIS = 500;
    private LocalTime lastShootTime = LocalTime.now();

    private boolean toggle;

    private boolean readyToChange() {
        return LocalTime.now().isAfter(lastShootTime.plus(CHANGE_TOGGLE_RELOAD_MILLIS, ChronoUnit.MILLIS));
    }

    public Toggle(boolean initState) {
        toggle = initState;
    }

    public boolean isOn() {
        return toggle;
    }

    public void switchToggle(){
        if (readyToChange()) {
            toggle = !toggle;
            lastShootTime = LocalTime.now();
        }
    }
}