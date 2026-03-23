package com.github.Jaecuber.Runeguard;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.Jaecuber.Runeguard.asset.AssetService;
import com.github.Jaecuber.Runeguard.asset.MapAsset;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen extends ScreenAdapter {
    private final Launcher game;
    private final Batch batch;
    private final AssetService assetService;
    private final Viewport viewport;
    private final OrthographicCamera camera;

    private final OrthogonalTiledMapRenderer mapRenderer;

    public GameScreen(Launcher game) {
        this.game = game;
        this.assetService = game.getAssetService();
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this.batch = game.getBatch();
        this.mapRenderer = new OrthogonalTiledMapRenderer(null, Launcher.UNIT_SCALE, this.batch);
    }

    @Override
    public void show(){
        this.assetService.load(MapAsset.MAIN);
        this.mapRenderer.setMap(this.assetService.get(MapAsset.MAIN));
    }

    @Override
    public void render(float delta){
        this.viewport.apply();
        this.batch.setColor(Color.WHITE);
        this.mapRenderer.setView(this.camera);
        this.mapRenderer.render();
    }

    @Override
    public void dispose(){
        this.mapRenderer.dispose();
    }
}