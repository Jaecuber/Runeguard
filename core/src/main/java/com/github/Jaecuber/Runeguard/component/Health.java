package com.github.Jaecuber.Runeguard.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.MathUtils;

public class Health implements Component{
    public static final ComponentMapper<Health> MAPPER = ComponentMapper.getFor(Health.class);

    private float maxHealth;
    private float health;
    private float regen;
    private float defaultMaxHealth;
    private float defaultRegen;

    public Health(float maxHealth, float regen){
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.regen = regen;
        this.defaultMaxHealth = maxHealth;
        this.defaultRegen = regen;
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

    public void setMaxHealth(float health){
        this.maxHealth = health;
    }

    public void setHealthRegen(float regen){
        this.regen = regen;
    }
    
    public float getDefaultMaxHealth() {
        return defaultMaxHealth;
    }

    public void setDefaultMaxHealth(float defaultMaxHealth) {
        this.defaultMaxHealth = defaultMaxHealth;
    }

    public float getDefaultRegen() {
        return defaultRegen;
    }

    public void setDefaultRegen(float defaultRegen) {
        this.defaultRegen = defaultRegen;
    }

    public void setHealth(float health){
        this.health = health;
    }

    public float getRegen(){
        return regen;
    }
    
}
