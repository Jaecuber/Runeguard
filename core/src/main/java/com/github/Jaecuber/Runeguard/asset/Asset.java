package com.github.Jaecuber.Runeguard.asset;

import com.badlogic.gdx.assets.AssetDescriptor;

public interface Asset<T> {
    AssetDescriptor<T> getDescriptor();
}
