package com.github.Jaecuber.Runeguard.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.github.Jaecuber.Runeguard.component.Animation2D;
import com.github.Jaecuber.Runeguard.component.Enemy;
import com.github.Jaecuber.Runeguard.component.Animation2D.AnimationType;
import com.github.Jaecuber.Runeguard.component.Enemy.EnemyAIState;

public enum EnemyState implements State<Entity>{
    IDLE{
        @Override
        public void enter(Entity entity) {
            Enemy.MAPPER.get(entity).setState(EnemyAIState.IDLE);
            Animation2D.MAPPER.get(entity).setType(AnimationType.IDLE);
        }

        @Override
        public void update(Entity entity) {
            
        }

        @Override
        public void exit(Entity entity) {
            
        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    },
    WANDERING{
        @Override
        public void enter(Entity entity) {
            Enemy.MAPPER.get(entity).setState(EnemyAIState.WANDERING);
        }

        @Override
        public void update(Entity entity) {
            
        }

        @Override
        public void exit(Entity entity) {
            
        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    },
    PURSUING{
        @Override
        public void enter(Entity entity) {
            Enemy.MAPPER.get(entity).setState(EnemyAIState.PURSUING);
        }

        @Override
        public void update(Entity entity) {
            
        }

        @Override
        public void exit(Entity entity) {
            
        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    },
    ATTACKING{
        @Override
        public void enter(Entity entity) {
            Enemy.MAPPER.get(entity).setState(EnemyAIState.ATTACKING);
        }

        @Override
        public void update(Entity entity) {
            
        }

        @Override
        public void exit(Entity entity) {
            
        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    }
}
