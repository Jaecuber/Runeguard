package com.github.Jaecuber.Runeguard.combat;

import com.badlogic.ashley.core.Entity;

public interface EnemyMoveset {
    void attack(Entity entity, Entity player);
}
