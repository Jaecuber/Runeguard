package com.github.Jaecuber.Runeguard.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
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

        dodge.tickImmuneTimer(deltaTime);
        if(dodge.canDodge()){
            move.setDoingAction(false);
            return;
        }
        dodge.tickDodgeTimer(deltaTime);
    }
}
