package com.github.Jaecuber.Runeguard.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Queue;
import com.github.Jaecuber.Runeguard.asset.AssetService;
import com.github.Jaecuber.Runeguard.asset.JsonAsset;
import com.github.Jaecuber.Runeguard.asset.MusicAsset;
import com.github.Jaecuber.Runeguard.asset.SoundAsset;
import com.github.Jaecuber.Runeguard.audio.AudioService;
import com.github.Jaecuber.Runeguard.component.Enemy;
import com.github.Jaecuber.Runeguard.data.EnemyBag;
import com.github.Jaecuber.Runeguard.data.EnemyEntry;
import com.github.Jaecuber.Runeguard.logic.RunManager.RunState;
import com.github.Jaecuber.Runeguard.tiled.EntitySpawner;
import com.github.Jaecuber.Runeguard.tiled.TiledService;
import com.github.Jaecuber.ui.model.GameViewModel;

public class LevelRunner {
    private final float REGULAR_WAVE_TIME = 10.0f; //10 Seconds
    private final int WAVES_PER_LEVEL = 10;
    private final int MID_UPGRADE_WAVE = 5;

    private TiledService tiledService;
    private EntitySpawner entitySpawner;
    private GameViewModel viewModel;
    private Engine engine;
    private AudioService audioService;

    private MusicAsset currentMusic;

    private Array<EnemyEntry> enemyBag;

    private float difficulty;
    private float waveTimer;
    private int level;
    private int wave;
    private int prevEnemyAmt;
    private boolean actingMidUpgrade = false;
    
    public LevelRunner(TiledService tiledService, EntitySpawner entitySpawner, Engine engine, AssetService assetService, GameViewModel viewModel, AudioService audioService){
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.viewModel = viewModel;
        this.audioService = audioService;
        this.engine = engine;
        this.waveTimer = REGULAR_WAVE_TIME;
        this.level = 0;
        this.wave = 1;
        this.prevEnemyAmt = engine.getEntitiesFor(Family.all(Enemy.class).get()).size();

        //loading enemy bag
        String raw = assetService.get(JsonAsset.ENEMY_BAG);
        Json json = new Json();
        EnemyBag bag = json.fromJson(EnemyBag.class, raw);
        enemyBag = bag.getEnemies();
    }
    
    public void update(float deltaTime, RunState runState){
        if(runState.equals(RunState.PLAYING)){
            tickWaveTimer(deltaTime);
        }
        if(prevEnemyAmt != engine.getEntitiesFor(Family.all(Enemy.class).get()).size()){
            prevEnemyAmt = engine.getEntitiesFor(Family.all(Enemy.class).get()).size();
            viewModel.updateEnemyCount(prevEnemyAmt);
        }
    }

    public void runLevel(){
        this.level++;
        this.wave = 1;
        this.waveTimer = REGULAR_WAVE_TIME;
        difficulty = calcDifficulty(level, wave);
        viewModel.updateLevel(level);
        viewModel.updateWave(wave);
        spawnWave(difficulty);
        
        switch (this.level) {
            case 1 -> {this.currentMusic = MusicAsset.LEVEL1;}
            case 2 -> {this.currentMusic = MusicAsset.LEVEL2;}
            case 3 -> {this.currentMusic = MusicAsset.LEVEL3TO4;}
            case 5 -> {this.currentMusic = MusicAsset.LEVEL5TO7;}
            case 8 -> {this.currentMusic = MusicAsset.LEVEL8INF;}
        }
        audioService.playMusic(currentMusic);
    }

    private void nextWave(){
        this.wave++;
        this.waveTimer = REGULAR_WAVE_TIME;
        difficulty = calcDifficulty(level, wave);
        viewModel.updateLevel(level);
        viewModel.updateWave(wave);
        
        if(wave == MID_UPGRADE_WAVE){
            this.actingMidUpgrade = true;
        }else{
            spawnWave(difficulty);
        }
    }

    public void clearMidUpgradeActor(){
        this.actingMidUpgrade = false;
    }

    public boolean isMidUpgrading(){
        return this.actingMidUpgrade;
    }

    public void spawnQueuedWave(){
        audioService.playMusic(currentMusic);
        spawnWave(difficulty);
    }

    private float calcDifficulty(int level, int wave){
        return ((level - 1)*16 + wave) * (float) Math.pow(MathUtils.E, (float) 0.15 * (level - 1));
    }

    private void spawnWave(float difficulty){
        Array<Vector2> spawns = tiledService.getSpawns();
        Queue<String> enemyQueue = createQueue(difficulty);
        
        while(enemyQueue.notEmpty()){
            String enemy = enemyQueue.removeFirst();
            Vector2 randomSpawn = spawns.get((Integer) MathUtils.random(0, spawns.size - 1));
            entitySpawner.spawnEntity(enemy, randomSpawn);
        }
    }

    private Queue<String> createQueue(float difficulty){
        Queue<String> queue = new Queue<>();
        Array<String> validEnemies = getValidEnemies(difficulty);
        int numEnemies = MathUtils.round((float) (2 + 0.50 * Math.pow(difficulty, 0.65)));
        for(int i = 0; i < numEnemies; i++){
            queue.addFirst(validEnemies.get((Integer) MathUtils.random(0, validEnemies.size - 1)));
        }
        return queue;
    }

    private Array<String> getValidEnemies(float difficulty){
        Array<String> validEnemies = new Array<>();

        for(EnemyEntry enemy : enemyBag){
            if(enemy.getMinDiff() < difficulty){
                validEnemies.add(enemy.getName());
            }
        }

        return validEnemies;
    }

    public boolean levelComplete(){
        return wave >= WAVES_PER_LEVEL && waveTimer <= 0 && engine.getEntitiesFor(Family.all(Enemy.class).get()).size() == 0;
    }

    public boolean enemiesCleared(){
        return engine.getEntitiesFor(Family.all(Enemy.class).get()).size() == 0;
    }

    public void tickWaveTimer(float deltaTime){
        waveTimer -= deltaTime;
        viewModel.updateTimer(MathUtils.round(waveTimer));
        if(waveTimer <= 0){
            if(wave < WAVES_PER_LEVEL){
                nextWave();
            }
        }
    }
}
