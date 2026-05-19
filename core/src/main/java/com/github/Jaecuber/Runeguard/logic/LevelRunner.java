package com.github.Jaecuber.Runeguard.logic;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.github.Jaecuber.Runeguard.tiled.EntitySpawner;
import com.github.Jaecuber.Runeguard.tiled.TiledService;

public class LevelRunner {
    private final float REGULAR_WAVE_TIME = 60.0f; //60 Seconds

    private TiledService tiledService;
    private EntitySpawner entitySpawner;

    Array<String> enemyQueue = new Array<>();

    private float difficulty;
    private float waveTimer;
    private int level;
    private int wave;
    
    public LevelRunner(TiledService tiledService, EntitySpawner entitySpawner){
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.level = 0;
        this.wave = 1;
        this.difficulty = 1.5f;
    }
    //make tick wave timer method
    public void update(float deltaTime){
        tickWaveTimer(deltaTime);
    }

    public void runLevel(){
        this.level++;
        for(int i = 1; i <= 10; i++){
            wave = i;
            difficulty = ((level - 1)*16 + wave) * (float) Math.pow(MathUtils.E, (float) 0.15 * (level - 1));
        }
    }

    public void tickWaveTimer(float deltaTime){
        waveTimer -= deltaTime;
        if(waveTimer <= 0){
            waveTimer = REGULAR_WAVE_TIME;
            return;
        }
    }
}
