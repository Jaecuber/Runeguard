package com.github.Jaecuber.Runeguard.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;

public enum MusicAsset implements Asset<Music>{
    TOWN("start.wav"),
    MAIN_MENU("mainMenu.wav"),
    LEVEL1("level1.wav"),
    LEVEL2("level2.mp3"),
    LEVEL3TO4("level3-4.mp3"),
    LEVEL5TO7("level5-7.mp3"),
    LEVEL8INF("level8-inf.mp3"),
    UPGRADE("upgrade.mp3"),
    GAME_OVER("gameOver.mp3"),
    BEGINNING_AMBIENCE("beginningAmbience.mp3");

    private final AssetDescriptor<Music> descriptor;

    MusicAsset(String musicFile){
        this.descriptor = new AssetDescriptor<>("audio/music/" + musicFile, Music.class);
    }

    @Override
    public AssetDescriptor<Music> getDescriptor(){
        return descriptor;
    }
}
