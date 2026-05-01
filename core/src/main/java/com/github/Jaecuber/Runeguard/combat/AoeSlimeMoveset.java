package com.github.Jaecuber.Runeguard.combat;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.Jaecuber.Runeguard.component.Enemy;
import com.github.Jaecuber.Runeguard.component.Move;
import com.github.Jaecuber.Runeguard.component.Physics;

public class AoeSlimeMoveset implements EnemyMoveset{
    @Override
    public void attack(Entity entity, Entity player) {
        Enemy enemy = Enemy.MAPPER.get(entity);
        Body body = Physics.MAPPER.get(entity).getBody();
        Body playerBody = Physics.MAPPER.get(player).getBody();
        if(body == null || playerBody == null) return;

        enemy.applyAttack();
        Move.MAPPER.get(entity).setDoingAction(true);
    }

}
