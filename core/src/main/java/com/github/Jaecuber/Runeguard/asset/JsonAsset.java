package com.github.Jaecuber.Runeguard.asset;

import com.badlogic.gdx.assets.AssetDescriptor;

public enum JsonAsset implements Asset<String>{
    ENEMY_BAG("enemies.json");

    private final AssetDescriptor<String> descriptor;

    JsonAsset(String jsonName){
        this.descriptor = new AssetDescriptor<>("data/" + jsonName, String.class);
    }

    @Override
    public AssetDescriptor<String> getDescriptor(){
        return descriptor;
    }
}
