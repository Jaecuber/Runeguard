package com.github.Jaecuber.Runeguard.logic;

import com.github.Jaecuber.Runeguard.tiled.EntitySpawner;
import com.github.Jaecuber.Runeguard.tiled.TiledService;

public class RunManager{

    private RunState runState;
    private TiledService tiledService;
    private LevelRunner levelRunner;
    private EntitySpawner entitySpawner;
    private float difficulty;
    private float level;

    public RunManager(TiledService tiledService, EntitySpawner entitySpawner){
        this.runState = RunState.PLAYING;
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.difficulty = 1.0f;
        this.level = 1.0f;
        this.levelRunner = new LevelRunner(this.tiledService, this.entitySpawner, this.difficulty);
    }

    public void onStateChange(RunState stateChange){
        switch (stateChange) {
            case PLAYING -> playing();
            case LEVEL_CLEAR -> levelClear();
            case UPGRADING -> upgrading();
            case NEXT_LEVEL -> nextLevel();
            case RESTARTING_GAME -> restartGame();
            case CUTSCENE -> playCutscene();
        }
    }

    private void playCutscene() {
       
    }

    private void restartGame() {
        
    }

    private void nextLevel() {
        
    }

    private void upgrading() {
        
    }

    private void levelClear() {
        
    }

    private void playing(){
        
    }

    private float calcDifficulty(){
        return 0.0f;
    }

    public RunState getRunState(){
        return this.runState;
    }


    public void changeState(RunState runState){
        this.runState = runState;
    }

    public RunState getState(){
        return this.runState;
    }

    public enum RunState{
        PLAYING, LEVEL_CLEAR, UPGRADING, NEXT_LEVEL, RESTARTING_GAME, CUTSCENE
    }
}