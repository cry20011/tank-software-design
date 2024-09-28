package ru.mipt.bit.platformer.game.entities;

public interface Damagable {
    void receiveDamage(int damage);

    int getHealthPercent();
}
