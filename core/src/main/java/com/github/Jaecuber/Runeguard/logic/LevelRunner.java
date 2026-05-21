package com.github.Jaecuber.Runeguard.logic;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Queue;
import com.github.Jaecuber.Runeguard.asset.AssetService;
import com.github.Jaecuber.Runeguard.asset.JsonAsset;
import com.github.Jaecuber.Runeguard.component.Enemy;
import com.github.Jaecuber.Runeguard.data.EnemyBag;
import com.github.Jaecuber.Runeguard.data.EnemyEntry;
import com.github.Jaecuber.Runeguard.logic.RunManager.RunState;
import com.github.Jaecuber.Runeguard.tiled.EntitySpawner;
import com.github.Jaecuber.Runeguard.tiled.TiledService;

public class LevelRunner {
    private final float REGULAR_WAVE_TIME = 20.0f; //20 Seconds

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
        int numEnemies = MathUtils.round((float) (9 + 0.50 * Math.pow(difficulty, 0.65)));
        for(int i = 0; i < numEnemies; i++){
            queue.addFirst(validEnemies.get((Integer) MathUtils.random(0, validEnemies.size - 1)));
        }
        return queue;
    }

    private Array<String> getValidEnemies(float difficulty){
        Array<String> validEnemies = new Array<>();

        for(EnemyEntry enemy : enemyBag){
            validEnemies.add(enemy.getName());
        }

        return validEnemies;
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
