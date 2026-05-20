package com.github.Jaecuber.Runeguard.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.github.Jaecuber.Runeguard.asset.AssetService;
import com.github.Jaecuber.Runeguard.asset.JsonAsset;
import com.github.Jaecuber.Runeguard.component.Enemy;
import com.github.Jaecuber.Runeguard.data.EnemyBag;
import com.github.Jaecuber.Runeguard.data.EnemyEntry;
import com.github.Jaecuber.Runeguard.logic.RunManager.RunState;
import com.github.Jaecuber.Runeguard.tiled.EntitySpawner;
import com.github.Jaecuber.Runeguard.tiled.TiledService;

public class LevelRunner {
    private final float REGULAR_WAVE_TIME = 30.0f; //30 Seconds

    private TiledService tiledService;
    private EntitySpawner entitySpawner;
    private Engine engine;

    private Array<EnemyEntry> enemyBag;

    private float difficulty;
    private float waveTimer;
    private int level;
    private int wave;
    
    public LevelRunner(TiledService tiledService, EntitySpawner entitySpawner, Engine engine, AssetService assetService){
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.engine = engine;
        this.waveTimer = REGULAR_WAVE_TIME;
        this.level = 0;
        this.wave = 1;

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
    }

    public void runLevel(){
        this.level++;
        this.wave = 1;
        this.waveTimer = REGULAR_WAVE_TIME;
        difficulty = calcDifficulty(level, wave);
        spawnWave(difficulty);
    }

    private void nextWave(){
        this.wave++;
        this.waveTimer = REGULAR_WAVE_TIME;
        difficulty = calcDifficulty(level, wave);
        spawnWave(difficulty);
    }

    private float calcDifficulty(int level, int wave){
        return ((level - 1)*16 + wave) * (float) Math.pow(MathUtils.E, (float) 0.15 * (level - 1));
    }

    private void spawnWave(float difficulty){
        Array<Vector2> spawns = tiledService.getSpawns();
        Array<String> enemyQueue = createQueue(difficulty);
        
    }

    private Array<String> createQueue(float difficulty){
        Array<String> queue = new Array<>();
        
        return queue;
    }

    public boolean levelComplete(){
        return wave >= 10 && waveTimer <= 0 && engine.getEntitiesFor(Family.all(Enemy.class).get()).size() == 0;
    }

    public void tickWaveTimer(float deltaTime){
        waveTimer -= deltaTime;
        if(waveTimer <= 0){
            if(wave <= 10){
                nextWave();
            }else{
                wave = 0;
            }
        }
    }
}
