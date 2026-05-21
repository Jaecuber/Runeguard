package com.github.Jaecuber.Runeguard.logic;

import com.badlogic.ashley.core.Engine;
import com.github.Jaecuber.Runeguard.asset.AssetService;
import com.github.Jaecuber.Runeguard.tiled.EntitySpawner;
import com.github.Jaecuber.Runeguard.tiled.TiledService;

public class RunManager{

    private RunState runState;
    private TiledService tiledService;
    private LevelRunner levelRunner;
    private EntitySpawner entitySpawner;
    private AssetService assetService;
    private Engine engine;

    public RunManager(TiledService tiledService, EntitySpawner entitySpawner, Engine engine, AssetService assetService){
        this.runState = RunState.PLAYING;
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.assetService = assetService;
        this.engine = engine;

        this.levelRunner = new LevelRunner(this.tiledService, this.entitySpawner, this.engine, this.assetService);
    }

    public void update(float deltaTime){
        levelRunner.update(deltaTime, this.runState);
        switch (getState()) {
            case PLAYING -> playing();
            case LEVEL_CLEAR -> levelClear(deltaTime);
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

    private void levelClear(float deltaTime) {
        
    }

    private void playing(){
        if(levelRunner.levelComplete()){
            changeState(RunState.LEVEL_CLEAR);
        }
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