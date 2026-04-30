package com.github.Jaecuber.Runeguard.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.Runeguard.component.Animation2D;
import com.github.Jaecuber.Runeguard.component.Attack;
import com.github.Jaecuber.Runeguard.component.Dodge;
import com.github.Jaecuber.Runeguard.component.Move;

public class DodgeSystem extends IteratingSystem{
    public DodgeSystem(){
        super(Family.all(Dodge.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Dodge dodge = Dodge.MAPPER.get(entity);
        Move move = Move.MAPPER.get(entity);
        Animation2D animation = Animation2D.MAPPER.get(entity);
        Attack attack = Attack.MAPPER.get(entity);
        Float animSpeed = ((7/12f) / dodge.getDodgeCooldown()) + 0.025f;

        dodge.tickImmuneTimer(deltaTime);
        if(dodge.canDodge()){
            move.setDoingAction(false);
            if(attack.canAttack()){
                animation.setSpeed(1.0f);
            }
            return;
        }else{
            animation.setSpeed(animSpeed);
        }
        dodge.tickDodgeTimer(deltaTime);
    }
}
