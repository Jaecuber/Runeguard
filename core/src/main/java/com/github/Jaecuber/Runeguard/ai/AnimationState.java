package com.github.Jaecuber.Runeguard.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.github.Jaecuber.Runeguard.component.Animation2D;
import com.github.Jaecuber.Runeguard.component.Attack;
import com.github.Jaecuber.Runeguard.component.Enemy;
import com.github.Jaecuber.Runeguard.component.Fsm;
import com.github.Jaecuber.Runeguard.component.Health;
import com.github.Jaecuber.Runeguard.component.Move;
import com.github.Jaecuber.Runeguard.component.Player;
import com.github.Jaecuber.Runeguard.component.Animation2D.AnimationType;

public enum AnimationState implements State<Entity>{
    IDLE{
        @Override
        public void enter(Entity entity) {
            Animation2D.MAPPER.get(entity).setType(AnimationType.IDLE);
            Animation2D.MAPPER.get(entity).setPlayMode(PlayMode.LOOP);
        }

        @Override
        public void update(Entity entity) {
            Move move = Move.MAPPER.get(entity);
            Attack attack = Attack.MAPPER.get(entity);
            if(move != null && !move.isRooted() && !move.getDirection().isZero()){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(WALK);
                return;
            }
            if(attack != null && attack.isAttacking()){
                if(attack.getAttackAnim().equals("attack1")){
                    Fsm.MAPPER.get(entity).getAnimationFsm().changeState(ATTACK1);
                }else{
                    Fsm.MAPPER.get(entity).getAnimationFsm().changeState(ATTACK2);
                }
                return;
            }
            Enemy enemy = Enemy.MAPPER.get(entity);
            if(enemy != null && enemy.isStaggered()){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(HURT);
            }
            Health health = Health.MAPPER.get(entity);
            if(health != null && health.died() && Player.MAPPER.get(entity) != null){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(DEATH);
            }
        }

        @Override
        public void exit(Entity entity) {
            
        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }

    },
    WALK{

        @Override
        public void enter(Entity entity) {
            Animation2D.MAPPER.get(entity).setType(AnimationType.WALK);
            Animation2D.MAPPER.get(entity).setPlayMode(PlayMode.LOOP);
        }

        @Override
        public void update(Entity entity) {
            Move move = Move.MAPPER.get(entity);
            if(move == null || move.getDirection().isZero() || move.isRooted()){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(IDLE);
            }
            Enemy enemy = Enemy.MAPPER.get(entity);
            if(enemy != null && enemy.isStaggered()){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(HURT);
            }
            Health health = Health.MAPPER.get(entity);
            if(health != null && health.died() && Player.MAPPER.get(entity) != null){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(DEATH);
            }
        }

        @Override
        public void exit(Entity entity) {
          
        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
        
    },
    ATTACK1{
        @Override
        public void enter(Entity entity) {
            Animation2D.MAPPER.get(entity).setType(AnimationType.ATTACK1);
            Animation2D.MAPPER.get(entity).setPlayMode(PlayMode.LOOP);
        }

        @Override
        public void update(Entity entity) {
            Attack attack = Attack.MAPPER.get(entity);
            if(attack.canAttack()){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(IDLE);
            }
            Enemy enemy = Enemy.MAPPER.get(entity);
            if(enemy != null && enemy.isStaggered()){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(HURT);
            }
            Health health = Health.MAPPER.get(entity);
            if(health != null && health.died() && Player.MAPPER.get(entity) != null){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(DEATH);
            }
        }

        @Override
        public void exit(Entity entity) {
          
        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    },
    ATTACK2{
        @Override
        public void enter(Entity entity) {
            Animation2D.MAPPER.get(entity).setType(AnimationType.ATTACK2);
            Animation2D.MAPPER.get(entity).setPlayMode(PlayMode.LOOP);
        }

        @Override
        public void update(Entity entity) {
            Attack attack = Attack.MAPPER.get(entity);
            if(attack.canAttack()){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(IDLE);
            }
            Enemy enemy = Enemy.MAPPER.get(entity);
            if(enemy != null && enemy.isStaggered()){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(HURT);
            }
            Health health = Health.MAPPER.get(entity);
            if(health != null && health.died() && Player.MAPPER.get(entity) != null){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(DEATH);
            }
        }

        @Override
        public void exit(Entity entity) {
          
        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    },
    HURT{
        @Override
        public void enter(Entity entity) {
            Animation2D.MAPPER.get(entity).setType(AnimationType.HURT);
        }

        @Override
        public void update(Entity entity) {
            Enemy enemy = Enemy.MAPPER.get(entity);
            if (enemy != null && !enemy.isStaggered()) {
                Move move = Move.MAPPER.get(entity);
                if (move != null && !move.getDirection().isZero() && !move.isRooted()) {
                    Fsm.MAPPER.get(entity).getAnimationFsm().changeState(WALK);
                } else {
                    Fsm.MAPPER.get(entity).getAnimationFsm().changeState(IDLE);
                }
            }
        }

        @Override
        public void exit(Entity entity) {
          
        }

        @Override
        public boolean onMessage(Entity entity, Telegram telegram) {
            return false;
        }
    },
    DEATH{
        @Override
        public void enter(Entity entity) {
            Animation2D.MAPPER.get(entity).setType(AnimationType.DEATH);
            Animation2D.MAPPER.get(entity).setPlayMode(PlayMode.NORMAL);
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

