package com.github.Jaecuber.Runeguard.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;

public enum MusicAsset implements Asset<Music>{
    TOWN("start.wav"),
    MAIN_MENU("mainMenu.wav");

    private final AssetDescriptor<Music> descriptor;

    MusicAsset(String musicFile){
        this.descriptor = new AssetDescriptor<>("audio/music/" + musicFile, Music.class);
    }

    @Override
    public AssetDescriptor<Music> getDescriptor(){
        return descriptor;
    }
}
