package com.github.Jaecuber.Runeguard.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.Jaecuber.Runeguard.Launcher;
import com.github.Jaecuber.Runeguard.asset.MusicAsset;
import com.github.Jaecuber.Runeguard.asset.SkinAsset;
import com.github.Jaecuber.ui.model.MenuViewModel;
import com.github.Jaecuber.ui.view.MenuView;

public class MenuScreen extends ScreenAdapter{

    private final Launcher game;
    private final Stage stage;
    private final Skin skin;
    private final Viewport uiViewport;

    public MenuScreen (Launcher game){
        this.game = game;
        this.uiViewport = new FitViewport(1920f, 1152f);
        this.stage = new Stage(uiViewport, game.getBatch());
        this.skin = game.getAssetService().get(SkinAsset.DEFAULT);
    }

    @Override
    public void resize(int width, int height){
        uiViewport.update(width, height, true);
    }

    @Override
    public void show(){
        this.game.setInputProcessor(stage);

        this.stage.addActor(new MenuView(stage, skin, new MenuViewModel(game)));
        this.game.getAudioService().playMusic(MusicAsset.MAIN_MENU);
    }

    @Override
    public void hide(){
        this.stage.clear();
    }

    @Override
    public void render(float delta){
        uiViewport.apply();
        stage.getBatch().setColor(Color.WHITE);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose(){
        stage.dispose();
    }
}
