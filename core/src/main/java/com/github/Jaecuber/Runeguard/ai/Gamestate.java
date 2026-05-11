package com.github.Jaecuber.Runeguard.ai;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.github.Jaecuber.Runeguard.logic.RunManager;
import com.github.Jaecuber.Runeguard.logic.RunManager.RunState;

public enum Gamestate implements State<RunManager>{
    PLAYING{
        @Override
        public void enter(RunManager entity) {
            entity.onStateChange(RunState.PLAYING);
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
    LEVEL_CLEAR{
        @Override
        public void enter(RunManager entity) {
            entity.onStateChange(RunState.LEVEL_CLEAR);
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
    UPGRADING{
        @Override
        public void enter(RunManager entity) {
            entity.onStateChange(RunState.UPGRADING);
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
    NEXT_LEVEL{
        @Override
        public void enter(RunManager entity) {
            entity.onStateChange(RunState.NEXT_LEVEL);
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
    RESTARTING_GAME{
        @Override
        public void enter(RunManager entity) {
            entity.onStateChange(RunState.RESTARTING_GAME);
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
            entity.onStateChange(RunState.CUTSCENE);
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
