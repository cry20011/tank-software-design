package ru.mipt.bit.platformer.controllers;

import ru.mipt.bit.platformer.entities.MapObject;
import ru.mipt.bit.platformer.instructions.Instruction;

import java.util.Map;

public interface InstructionsGenerator {
    Map<MapObject, Instruction> generate();
}
