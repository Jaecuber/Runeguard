package com.github.Jaecuber.Runeguard.ai;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.github.Jaecuber.Runeguard.logic.RunManager;
import com.github.Jaecuber.Runeguard.logic.RunManager.RunState;

public enum GameState implements State<RunManager>{
    STARTING_LEVEL{
        @Override
        public void enter(RunManager entity) {
            entity.setState(RunState.STARTING_LEVEL);
        }
        @Override
        public void update(RunManager entity) {
            if(entity.startTimeOver()){
                entity.getGameFsm().changeState(NEXT_LEVEL);
            }
        }
        @Override
        public void exit(RunManager entity) {
            
        }
        @Override
        public boolean onMessage(RunManager entity, Telegram telegram) {
            return false;
        }
    },
    PLAYING{
        @Override
        public void enter(RunManager entity) {
            entity.setState(RunState.PLAYING);
        }
        @Override
        public void update(RunManager entity) {
            if(entity.levelComplete()){
                entity.getGameFsm().changeState(LEVEL_CLEAR);
            }
        }
        @Override
        public void exit(RunManager entity) {
            
        }
        @Override
        public boolean onMessage(RunManager entity, Telegram telegram) {
            return false;
        }
    },
    LEVEL_CLEAR{
        @Override
        public void enter(RunManager entity) {
            entity.setState(RunState.LEVEL_CLEAR);
        }
        @Override
        public void update(RunManager entity) {
            if(entity.endTimerOver()){
                entity.getGameFsm().changeState(UPGRADING);
            }
        }
        @Override
        public void exit(RunManager entity) {
            
        }
        @Override
        public boolean onMessage(RunManager entity, Telegram telegram) {
            return false;
        }
    },
    UPGRADING{
        @Override
        public void enter(RunManager entity) {
            entity.setState(RunState.UPGRADING);
        }
        @Override
        public void update(RunManager entity) {
            //do something then move to NEXT_LEVEL state
        }
        @Override
        public void exit(RunManager entity) {
            
        }
        @Override
        public boolean onMessage(RunManager entity, Telegram telegram) {
            return false;
        }
    },
    NEXT_LEVEL{
        @Override
        public void enter(RunManager entity) {
            entity.setState(RunState.NEXT_LEVEL);
        }
        @Override
        public void update(RunManager entity) {
            if(entity.getPlayingState()){
                entity.getGameFsm().changeState(PLAYING);
            }
        }
        @Override
        public void exit(RunManager entity) {
            
        }
        @Override
        public boolean onMessage(RunManager entity, Telegram telegram) {
            return false;
        }
    },
    RESTARTING_GAME{
        @Override
        public void enter(RunManager entity) {
            entity.setState(RunState.RESTARTING_GAME);
        }
        @Override
        public void update(RunManager entity) {
            
        }
        @Override
        public void exit(RunManager entity) {
            
        }
        @Override
        public boolean onMessage(RunManager entity, Telegram telegram) {
            return false;
        }
    },
    CUTSCENE{
        @Override
        public void enter(RunManager entity) {
            entity.setState(RunState.CUTSCENE);
        }
        @Override
        public void update(RunManager entity) {
            
        }
        @Override
        public void exit(RunManager entity) {
            
        }
        @Override
        public boolean onMessage(RunManager entity, Telegram telegram) {
            return false;
        }
    }
}
