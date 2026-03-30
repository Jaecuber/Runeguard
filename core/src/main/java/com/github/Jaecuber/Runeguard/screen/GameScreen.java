package com.github.Jaecuber.Runeguard.screen;

import java.util.function.Consumer;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.github.Jaecuber.Runeguard.Launcher;
import com.github.Jaecuber.Runeguard.asset.MapAsset;
import com.github.Jaecuber.Runeguard.audio.AudioService;
import com.github.Jaecuber.Runeguard.input.GameControllerState;
import com.github.Jaecuber.Runeguard.input.KeyboardController;
import com.github.Jaecuber.Runeguard.systems.AnimationSystem;
import com.github.Jaecuber.Runeguard.systems.CameraSystem;
import com.github.Jaecuber.Runeguard.systems.ControllerSystem;
import com.github.Jaecuber.Runeguard.systems.FacingSystem;
import com.github.Jaecuber.Runeguard.systems.FsmSystem;
import com.github.Jaecuber.Runeguard.systems.PhysicsMoveSystem;
import com.github.Jaecuber.Runeguard.systems.PhysicsDebugRenderSystem;
import com.github.Jaecuber.Runeguard.systems.PhysicsSystem;
import com.github.Jaecuber.Runeguard.systems.RenderSystem;
import com.github.Jaecuber.Runeguard.tiled.TiledAshleyConfig;
import com.github.Jaecuber.Runeguard.tiled.TiledService;

/** First screen of the application. Displayed after the application is created. */
public class GameScreen extends ScreenAdapter {
    private final Engine engine;
    private final TiledService tiledService;
    private final TiledAshleyConfig tiledAshleyConfig;
    private final KeyboardController keyboardController;
    private final Launcher game;
    private final World physicsWorld;
    private final AudioService audioService;

    public GameScreen(Launcher game) {
        this.game = game;
        this.physicsWorld = new World(Vector2.Zero, true);
        this.physicsWorld.setAutoClearForces(false);
        this.tiledService = new TiledService(game.getAssetService(), this.physicsWorld);
        this.engine = new Engine();
        this.tiledAshleyConfig = new TiledAshleyConfig(this.engine, game.getAssetService(), physicsWorld);
        this.keyboardController = new KeyboardController(GameControllerState.class, engine);
        this.audioService = game.getAudioService();
        
        this.engine.addSystem(new ControllerSystem(game.getAudioService()));
        this.engine.addSystem(new PhysicsMoveSystem());
        this.engine.addSystem(new FsmSystem());
        this.engine.addSystem(new FacingSystem());
        this.engine.addSystem(new PhysicsSystem(physicsWorld, 1/60f));
        this.engine.addSystem(new AnimationSystem(game.getAssetService()));
        this.engine.addSystem(new CameraSystem(game.getCamera()));
        this.engine.addSystem(new RenderSystem(game.getBatch(), game.getViewport(), game.getCamera()));
        this.engine.addSystem(new PhysicsDebugRenderSystem(physicsWorld, game.getCamera()));
    }

    @Override
    public void show(){
        game.setInputProcessor(keyboardController);
        keyboardController.setActiveState(GameControllerState.class);

        Consumer<TiledMap> renderConsumer = this.engine.getSystem(RenderSystem.class)::setMap;
        Consumer<TiledMap> cameraConsumer = this.engine.getSystem(CameraSystem.class)::setMap;
        Consumer<TiledMap> audioConsumer = audioService::setMap;

        this.tiledService.setMapChangeConsumer(renderConsumer.andThen(cameraConsumer).andThen(audioConsumer));
        this.tiledService.setLoadObjectConsumer(this.tiledAshleyConfig::onLoadObject);
        this.tiledService.setLoadTileConsumer(this.tiledAshleyConfig::onLoadTile);

        TiledMap tiledMap = this.tiledService.loadMap(MapAsset.MAIN);
        this.tiledService.setMap(tiledMap);
    }

    @Override
    public void hide(){
        this.engine.removeAllEntities();
    }

    @Override
    public void render(float delta){
        delta = Math.min(delta, 1 / 30f);
        this.engine.update(delta);
    }

    @Override
    public void dispose(){
        for(EntitySystem system : this.engine.getSystems()){
            if(system instanceof Disposable disposableSystem){
                disposableSystem.dispose();
            }
        }
        this.physicsWorld.dispose();
    }
}