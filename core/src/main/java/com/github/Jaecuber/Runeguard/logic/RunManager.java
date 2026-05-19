package com.github.Jaecuber.Runeguard.logic;

import com.github.Jaecuber.Runeguard.tiled.EntitySpawner;
import com.github.Jaecuber.Runeguard.tiled.TiledService;

public class RunManager{

    private RunState runState;
    private TiledService tiledService;
    private LevelRunner levelRunner;
    private EntitySpawner entitySpawner;

    public RunManager(TiledService tiledService, EntitySpawner entitySpawner){
        this.runState = RunState.PLAYING;
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.levelRunner = new LevelRunner(this.tiledService, this.entitySpawner);
    }

    public void update(float deltaTime){
        levelRunner.update(deltaTime);
        switch (getState()) {
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
        levelRunner.runLevel();
    }

    private void upgrading() {
        
    }

    private void levelClear() {
        
    }

    private void playing(){
        //state management
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