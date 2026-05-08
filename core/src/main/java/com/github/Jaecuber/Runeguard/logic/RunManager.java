package com.github.Jaecuber.Runeguard.logic;

public class RunManager{

    private RunState runState;
    private float difficulty;

    public RunManager(){

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