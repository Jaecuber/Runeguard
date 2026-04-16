package com.github.Jaecuber.Runeguard.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Health implements Component{
    public static final ComponentMapper<Health> MAPPER = ComponentMapper.getFor(Health.class);

    public float health;

    public Health(float health){
        this.health = health;
    }

    
}
