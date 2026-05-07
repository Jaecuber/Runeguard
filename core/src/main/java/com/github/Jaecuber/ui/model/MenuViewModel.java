package com.github.Jaecuber.ui.model;

import com.badlogic.gdx.Gdx;
import com.github.Jaecuber.Runeguard.Launcher;
import com.github.Jaecuber.Runeguard.asset.MapAsset;
import com.github.Jaecuber.Runeguard.asset.SoundAsset;
import com.github.Jaecuber.Runeguard.audio.AudioService;
import com.github.Jaecuber.Runeguard.screen.GameScreen;

public class MenuViewModel extends ViewModel{
    private final AudioService audioService;

    public MenuViewModel(Launcher game){
        super(game);
        this.audioService = game.getAudioService();
    }

    public float getMusicVolume(){
        return audioService.getMusicVolume();
    }

    public float getSoundVolume(){
        return audioService.getSoundVolume();
    }

    public void setMusicVolume(float volume){
        this.audioService.setMusicVolume(volume);
    }

    public void setSoundVolume(float volume){
        this.audioService.setSoundVolume(volume);
    }
    public void startGame(){
        game.setScreen(new GameScreen(game, MapAsset.DUNGEON));
    }

    public void playSound(SoundAsset sound){
        this.game.getAudioService().playSound(sound);
    }

    public void quitGame(){
        Gdx.app.exit();
    }
}
