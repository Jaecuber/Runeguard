package com.github.Jaecuber.Runeguard.ai;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.github.Jaecuber.Runeguard.component.Animation2D;
import com.github.Jaecuber.Runeguard.component.Attack;
import com.github.Jaecuber.Runeguard.component.Fsm;
import com.github.Jaecuber.Runeguard.component.Move;
import com.github.Jaecuber.Runeguard.component.Animation2D.AnimationType;

public enum AnimationState implements State<Entity>{
    IDLE{
        @Override
        public void enter(Entity entity) {
            Animation2D.MAPPER.get(entity).setType(AnimationType.IDLE);
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
        }

        @Override
        public void update(Entity entity) {
            Move move = Move.MAPPER.get(entity);
            if(move == null || move.getDirection().isZero() || move.isRooted()){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(IDLE);
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
        }

        @Override
        public void update(Entity entity) {
            Attack attack = Attack.MAPPER.get(entity);
            if(attack.canAttack()){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(IDLE);
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
        }

        @Override
        public void update(Entity entity) {
            Attack attack = Attack.MAPPER.get(entity);
            if(attack.canAttack()){
                Fsm.MAPPER.get(entity).getAnimationFsm().changeState(IDLE);
            }
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
