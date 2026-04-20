package com.github.Jaecuber.Runeguard.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.Runeguard.component.Health;
import com.github.Jaecuber.Runeguard.component.Player;

public class HealthSystem extends IteratingSystem implements EntityListener{

    public HealthSystem(){
        super(Family.all(Health.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Health health = Health.MAPPER.get(entity);
        if(health.getHealth() == health.getMaxHealth()) return;

        health.addHealth(health.getRegen() * deltaTime);
        if(health.died() && Player.MAPPER.get(entity) != null){
            die(entity);
        }
    }

    private void die(Entity entity){
        
    }

    @Override
    public void entityAdded(Entity entity) {
    }

    @Override
    public void entityRemoved(Entity entity) {
    }

}
