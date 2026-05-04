package com.github.Jaecuber.ui.model;

import java.util.Map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.github.Jaecuber.Runeguard.Launcher;
import com.github.Jaecuber.Runeguard.asset.MapAsset;
import com.github.Jaecuber.Runeguard.asset.SoundAsset;
import com.github.Jaecuber.Runeguard.screen.GameScreen;
import com.github.Jaecuber.Runeguard.tiled.EntitySpawner;
import com.github.Jaecuber.Runeguard.tiled.TiledAshleyConfig;
import com.github.Jaecuber.Runeguard.tiled.TiledService;

public class GameViewModel extends ViewModel{
    public static final String PLAYER_DAMAGE = "playerDamage";
    public static final String HEALTH = "health";
    public static final String MAX_HEALTH = "maxHealth";
    public static final String STAMINA = "stamina";
    public static final String MAX_STAMINA = "maxStamina";
    public static final String GAME_OVER = "gameOver";

    private Map.Entry<Vector2, Integer> playerDamage;
    private int health;
    private int maxHealth;
    private int stamina;
    private int maxStamina;
    private final Vector2 tempVec2;
    private TiledService tiledService;
    private EntitySpawner entitySpawner;

    public GameViewModel(Launcher game, TiledService tiledService, EntitySpawner entitySpawner){
        super(game);
        this.tempVec2 = new Vector2();
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
    }

    public void updateHealthInfo(float maxHealth, float health){
        setMaxHP((int) maxHealth);
        setHP((int) health);
    }
    public void updateStaminaInfo(float maxStamina, float stamina){
        setMaxStamina((int) maxStamina);
        setStamina((int) stamina);
    }
    public void showGameOver(){
        this.propertyChangeSupport.firePropertyChange(GAME_OVER, false, true);
    }

    public void spawnEnemy(){
        entitySpawner.spawnEntity("green_slime", new Vector2(301.75f, 501.75f));
    }

    public void continueGame(){
        game.setScreen(new GameScreen(game, MapAsset.MAIN));
        updateHealthInfo(maxHealth, health);
        updateStaminaInfo(maxStamina, maxStamina);
    }

    public void changeMap(MapAsset mapAsset){
        TiledMap tiledMap = this.tiledService.loadMap(mapAsset);
        this.tiledService.setMap(tiledMap);
    }

    public void setMaxHP(int maxHP) {
        if (this.maxHealth != maxHP) {
            this.propertyChangeSupport.firePropertyChange(MAX_HEALTH, this.maxHealth, maxHP);
        }
        this.maxHealth = maxHP;
    }

    public void setMaxStamina(int maxStamina){
        if(this.maxStamina != maxStamina){
            this.propertyChangeSupport.firePropertyChange(MAX_STAMINA, this.maxStamina, maxStamina);
        }
        this.maxStamina = maxStamina;
    }

    public int getMaxHP() {
        return maxHealth;
    }

    public int getMaxStamina(){
        return maxStamina;
    }

    public void setHP(int HP){
        if(this.health != HP){
            this.propertyChangeSupport.firePropertyChange(HEALTH, this.health, HP);
        }
        this.health = HP;
    }

    public void setStamina(int stamina){
        if(this.stamina != stamina){
            this.propertyChangeSupport.firePropertyChange(STAMINA, this.stamina, stamina);
        }
        this.stamina = stamina;
    }

    public void playerDamage(int amount, float x, float y){
        float randomNumX = MathUtils.random(0.0f, 2.0f);
        float randomNumY = MathUtils.random(0.0f, 2.0f);
        Vector2 position = new Vector2(x + randomNumX,y + randomNumY);
        this.playerDamage = Map.entry(position, amount);
        this.propertyChangeSupport.firePropertyChange(PLAYER_DAMAGE, null, this.playerDamage);
    }

    public void quitGame(){
        Gdx.app.exit();
    }

    public void playSound(SoundAsset sound){
        this.game.getAudioService().playSound(sound);
    }

    public Vector2 toScreenCoords(Vector2 position) {
        tempVec2.set(position);
        game.getViewport().project(tempVec2);
        return tempVec2;
    }
}
