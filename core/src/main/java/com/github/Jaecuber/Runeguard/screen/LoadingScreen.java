package com.github.Jaecuber.Runeguard.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.github.Jaecuber.Runeguard.Launcher;
import com.github.Jaecuber.Runeguard.asset.AssetService;
import com.github.Jaecuber.Runeguard.asset.AtlasAsset;
import com.github.Jaecuber.Runeguard.asset.MapAsset;
import com.github.Jaecuber.Runeguard.asset.SkinAsset;
import com.github.Jaecuber.Runeguard.asset.SoundAsset;

public class LoadingScreen extends ScreenAdapter{
    private final Launcher game;
    private final AssetService assetService;

    public LoadingScreen(Launcher game, AssetService assetService) {
        this.game = game;
        this.assetService = assetService;
    }

    @Override
    public void show(){
        for(AtlasAsset atlas : AtlasAsset.values()){
            assetService.queue(atlas);
        }
        for(SoundAsset sound : SoundAsset.values()){
            assetService.queue(sound);
        }
        assetService.queue(SkinAsset.DEFAULT);
    }

    @Override
    public void render(float delta){
        if(this.assetService.update()){
            Gdx.app.debug("Loading Screen", "Finished asset loading");
            createScreens();
            this.game.removeScreen(this);
            this.dispose();
            this.game.setScreen(MenuScreen.class);
        }
    }

    private void createScreens(){
        this.game.addScreen(new MenuScreen(this.game));
        this.game.addScreen(new GameScreen(this.game, MapAsset.DUNGEON));
    }
}
