package com.github.Jaecuber.Runeguard.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Enemy implements Component{
    public static final ComponentMapper<Enemy> MAPPER = ComponentMapper.getFor(Enemy.class);

    private EnemyAIState state;

    public Enemy(EnemyAIState state){
        this.state = state;
    }

    public void setState(EnemyAIState state){
        this.state = state;
    }

    public EnemyAIState getState(){
        return state;
    }

    public enum EnemyAIState{
        IDLE, WANDERING, PURSUING, ATTACKING
    }
}
