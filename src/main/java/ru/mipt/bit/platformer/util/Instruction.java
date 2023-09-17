package ru.mipt.bit.platformer.util;

public enum Instruction {
    GO_UP("Y", 1, 90f),
    GO_DOWN("Y", -1, -90f),
    GO_RIGHT("X", 1, 0f),
    GO_LEFT("X", -1, -180f);

    public String getDirection() {
        return direction;
    }

    public int getRange() {
        return range;
    }

    private final String direction;
    private final int range;

    public float getRotation() {
        return rotation;
    }

    private final float rotation;

    Instruction(String direction, int range, float rotation) {
        this.direction = direction;
        this.range = range;
        this.rotation = rotation;
    }

}
