package com.github.Jaecuber.Runeguard.combat;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.Jaecuber.Runeguard.component.Physics;

public class SlimeMoveset implements EnemyMoveset{

    @Override
    public void attack(Entity entity, Entity player) {
        Body body = Physics.MAPPER.get(entity).getBody();
        Body playerBody = Physics.MAPPER.get(player).getBody();

        if(body == null || playerBody == null) return;
        Vector2 attackVector = new Vector2(
            playerBody.getPosition().x - body.getPosition().x,
            playerBody.getPosition().y - body.getPosition().y
        ).nor().scl(50f);
         body.applyLinearImpulse(attackVector, body.getWorldCenter(), true);
    }

}
