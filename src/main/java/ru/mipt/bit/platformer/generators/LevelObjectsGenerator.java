package ru.mipt.bit.platformer.generators;

import ru.mipt.bit.platformer.common.Level;

public interface LevelObjectsGenerator {
    Level generateAndAdd();
}
