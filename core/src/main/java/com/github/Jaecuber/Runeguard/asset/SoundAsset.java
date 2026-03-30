package com.github.Jaecuber.Runeguard.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;

public enum SoundAsset implements Asset<Sound>{
    SWORD_HIT("swordSlash.mp3");

    private final AssetDescriptor<Sound> descriptor;

    SoundAsset(String soundFile){
        this.descriptor = new AssetDescriptor<>("audio/sfx/" + soundFile, Sound.class);
    }

    @Override
    public AssetDescriptor<Sound> getDescriptor(){
        return descriptor;
    }
}
