package com.github.Jaecuber.Runeguard.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.github.Jaecuber.Runeguard.ai.GameState;
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
    private DefaultStateMachine<RunManager, GameState> gameFsm;

    public RunManager(TiledService tiledService, EntitySpawner entitySpawner, Engine engine, AssetService assetService){
        this.runState = RunState.INTERMISSION;
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.assetService = assetService;
        this.engine = engine;
        this.gameFsm = new DefaultStateMachine<RunManager, GameState>(this, GameState.CUTSCENE);

        this.levelRunner = new LevelRunner(this.tiledService, this.entitySpawner, this.engine, this.assetService);
    }

    public void update(float deltaTime){
        levelRunner.update(deltaTime, this.runState);
        switch (getState()) {
            case INTERMISSION -> intermission(deltaTime);
            case PLAYING -> playing();
            case LEVEL_CLEAR -> levelClear(deltaTime);
            case UPGRADING -> upgrading();
            case NEXT_LEVEL -> nextLevel();
            case RESTARTING_GAME -> restartGame();
            case CUTSCENE -> playCutscene();
        }
    }

    private void intermission(float deltaTime) {
        
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

    }

    public boolean levelComplete(){
        return levelRunner.levelComplete();
    }

    public RunState getRunState(){
        return this.runState;
    }


    public void setState(RunState runState){
        this.runState = runState;
    }

    public RunState getState(){
        return this.runState;
    }

    public DefaultStateMachine<RunManager, GameState> getGameFsm(){
        return this.gameFsm;
    }

    public enum RunState{
        INTERMISSION, PLAYING, LEVEL_CLEAR, UPGRADING, NEXT_LEVEL, RESTARTING_GAME, CUTSCENE
    }
}