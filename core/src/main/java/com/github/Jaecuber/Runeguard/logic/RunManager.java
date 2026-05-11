package com.github.Jaecuber.Runeguard.logic;

import com.github.Jaecuber.Runeguard.tiled.EntitySpawner;
import com.github.Jaecuber.Runeguard.tiled.TiledService;

public class RunManager{

    private RunState runState;
    private TiledService tiledService;
    private EntitySpawner entitySpawner;
    private float difficulty;
    private int level;

    public RunManager(TiledService tiledService, EntitySpawner entitySpawner){

    }

    public RunState getRunState(){
        return this.runState;
    }

    public enum RunState{
        PLAYING,
        LEVEL_CLEAR,
        UPGRADING,
        LOADING_NEXT_LEVEL,
        RESTARTING_GAME
    }
}