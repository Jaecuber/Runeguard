package com.github.Jaecuber.Runeguard.logic;

import com.badlogic.ashley.core.Entity;
import com.github.Jaecuber.Runeguard.tiled.EntitySpawner;
import com.github.Jaecuber.Runeguard.tiled.TiledService;

public class LevelRunner {
    private TiledService tiledService;
    private EntitySpawner entitySpawner;
    private float difficulty;
    private float waveTimer;
    private int wave = 1;
    
    public LevelRunner(TiledService tiledService, EntitySpawner entitySpawner, float difficulty){
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.difficulty = difficulty;
    }
    //make tick wave timer method
    public void update(float deltaTime){

    }

    public void runLevel(){
        
    }
}
