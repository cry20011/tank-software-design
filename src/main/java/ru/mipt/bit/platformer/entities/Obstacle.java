package ru.mipt.bit.platformer.entities;

public class Obstacle extends MapObject {
    public Obstacle(String textureFileName, int x, int y) {
        super(textureFileName, x, y, 0f);
    }
}
