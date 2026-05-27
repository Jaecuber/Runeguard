package com.github.Jaecuber.Runeguard.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.math.MathUtils;
import com.github.Jaecuber.Runeguard.ai.GameState;
import com.github.Jaecuber.Runeguard.asset.AssetService;
import com.github.Jaecuber.Runeguard.asset.MusicAsset;
import com.github.Jaecuber.Runeguard.audio.AudioService;
import com.github.Jaecuber.Runeguard.input.GameControllerState;
import com.github.Jaecuber.Runeguard.input.IdleControllerState;
import com.github.Jaecuber.Runeguard.input.KeyboardController;
import com.github.Jaecuber.Runeguard.tiled.EntitySpawner;
import com.github.Jaecuber.Runeguard.tiled.TiledService;
import com.github.Jaecuber.ui.model.GameViewModel;

public class RunManager{
    private RunState runState;
    private TiledService tiledService;
    private LevelRunner levelRunner;
    private EntitySpawner entitySpawner;
    private AssetService assetService;
    private GameViewModel viewModel;
    private AudioService audioService;
    private KeyboardController keyboardController;
    private Engine engine;
    private DefaultStateMachine<RunManager, GameState> gameFsm;

    //handlers
    private boolean playingState = false;
    private boolean prompted = false;
    private boolean controllerStateSet = false;

    //timers
    private float startLevelTimer = 5.0f; //5 seconds
    private float endTimer = 5.0f; // 5 seconds
    private float intermissionTimer = 10.0f; //10 seconds;

    public RunManager(TiledService tiledService, EntitySpawner entitySpawner, Engine engine, AssetService assetService, GameViewModel viewModel, AudioService audioService, KeyboardController keyboardController){
        this.runState = RunState.STARTING_LEVEL;
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.assetService = assetService;
        this.audioService = audioService;
        this.keyboardController = keyboardController;
        this.engine = engine;
        this.viewModel = viewModel;
        this.gameFsm = new DefaultStateMachine<RunManager, GameState>(this, GameState.STARTING_LEVEL);

        this.levelRunner = new LevelRunner(this.tiledService, this.entitySpawner, this.engine, this.assetService, this.viewModel, this.audioService);
    }

    public void update(float deltaTime){
        levelRunner.update(deltaTime, this.runState);
        gameFsm.update();
        switch (getState()) {
            case STARTING_LEVEL -> startingLevel(deltaTime);
            case PLAYING -> playing();
            case LEVEL_CLEAR -> levelClear(deltaTime);
            case UPGRADING -> upgrading();
            case MID_UPGRADE -> upgrading();
            case INTERMISSION -> intermission(deltaTime);
            case NEXT_LEVEL -> nextLevel();
            case RESTARTING_GAME -> restartGame();
            case CUTSCENE -> playCutscene();
        }
    }

    private void intermission(float deltaTime) {
        if(!controllerStateSet){
            this.controllerStateSet = true;
            this.keyboardController.setActiveState(GameControllerState.class);
        }   
        tickIntermissionTime(deltaTime);
    }

    private void startingLevel(float deltaTime) {
        if(!controllerStateSet){
            this.controllerStateSet = true;
            this.keyboardController.setActiveState(GameControllerState.class);
        }  
        tickStartTime(deltaTime);
    }

    private void playCutscene() {
        
    }

    private void restartGame() {
        
    }

    private void nextLevel() {
        levelRunner.runLevel();
        playingState = true;
    }

    private void upgrading() {
        if(!prompted && enemiesCleared()){
            this.controllerStateSet = false;
            this.keyboardController.setActiveState(IdleControllerState.class);
            audioService.playMusic(MusicAsset.UPGRADE);
            prompted = true;
            viewModel.promptUpgrade();//add param to differentiate between mid upgrade and end upgrade
        }    
    }

    private void levelClear(float deltaTime) {
        tickEndTime(deltaTime);
    }

    private void playing(){
        prompted = false;
        playingState = false;
    }

    public boolean levelComplete(){
        return levelRunner.levelComplete();
    }

    public void continueLevel(){
        this.levelRunner.clearMidUpgradeActor();
        this.levelRunner.spawnQueuedWave();
    }

    public boolean isMidUpgrading(){
        return this.levelRunner.isMidUpgrading();
    }

    public boolean enemiesCleared(){
        return this.levelRunner.enemiesCleared();
    }

    private void tickEndTime(float deltaTime) {
        endTimer -= deltaTime;
        viewModel.updateTimer(MathUtils.round(endTimer));
    }

    private void tickIntermissionTime(float deltaTime) {
        intermissionTimer -= deltaTime;
        viewModel.updateTimer(MathUtils.round(intermissionTimer));
    }

    private void tickStartTime(float deltaTime) {
        startLevelTimer -= deltaTime;
        viewModel.updateTimer(MathUtils.round(startLevelTimer));
    }

    public boolean startTimeOver(){
        return startLevelTimer <= 0;
    } 

    public boolean endTimerOver(){
        return endTimer <= 0;
    } 

    public boolean intermissionTimerOver(){
        return intermissionTimer <= 0;
    }

    public boolean getPlayingState(){
        return playingState;
    }

    public boolean upgraded(){
        return viewModel.hadUpgraded();
    }

    public RunState getRunState(){
        return this.runState;
    }

    public void setState(RunState runState){
        switch (runState) {
            case STARTING_LEVEL -> {this.startLevelTimer = 5.0f;}
            case CUTSCENE -> {}
            case LEVEL_CLEAR -> {this.endTimer = 5.0f;}
            case NEXT_LEVEL -> {}
            case PLAYING -> {}
            case INTERMISSION -> {this.intermissionTimer = 10.0f;}
            case RESTARTING_GAME -> {} 
            case UPGRADING -> {}
            default -> {}
        }
        this.runState = runState;
    }

    public RunState getState(){
        return this.runState;
    }

    public DefaultStateMachine<RunManager, GameState> getGameFsm(){
        return this.gameFsm;
    }

    public enum RunState{
        STARTING_LEVEL, PLAYING, LEVEL_CLEAR, UPGRADING, MID_UPGRADE, INTERMISSION, NEXT_LEVEL, RESTARTING_GAME, CUTSCENE
    }
}