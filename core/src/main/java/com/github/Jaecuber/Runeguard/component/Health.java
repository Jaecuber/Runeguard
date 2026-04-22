package com.github.Jaecuber.Runeguard.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.MathUtils;

public class Health implements Component{
    public static final ComponentMapper<Health> MAPPER = ComponentMapper.getFor(Health.class);

    public float maxHealth;
    public float health;
    public float regen;

    public Health(float maxHealth, float regen){
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.regen = regen;
    }

    public boolean died(){
        return health <= 0;
    }

    public float getMaxHealth(){
        return maxHealth;
    }

    public float getHealth(){
        return health;
    }

    public void addHealth(float val){
        this.health = MathUtils.clamp(health += val, -1.0f, maxHealth);
    }

    public float getRegen(){
        return regen;
    }
    
}
