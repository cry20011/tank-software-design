package ru.mipt.bit.platformer.instructions;

import ru.mipt.bit.platformer.entities.MapObject;

public interface Instruction {
    void apply(MapObject object);
}
