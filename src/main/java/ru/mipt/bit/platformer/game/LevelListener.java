package ru.mipt.bit.platformer.game;

import ru.mipt.bit.platformer.game.entities.Player;

public interface LevelListener {
    void add(GameObject object);
    void addPlayer(Player player);
    void remove(GameObject object);
}
