package com.github.Jaecuber.ui.model;

import java.util.Map;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.github.Jaecuber.Runeguard.Launcher;
import com.github.Jaecuber.Runeguard.asset.JsonAsset;
import com.github.Jaecuber.Runeguard.asset.MapAsset;
import com.github.Jaecuber.Runeguard.asset.MusicAsset;
import com.github.Jaecuber.Runeguard.asset.SoundAsset;
import com.github.Jaecuber.Runeguard.component.Health;
import com.github.Jaecuber.Runeguard.component.Player;
import com.github.Jaecuber.Runeguard.component.UpgradeTags;
import com.github.Jaecuber.Runeguard.data.UpgradeBag;
import com.github.Jaecuber.Runeguard.data.UpgradeClass;
import com.github.Jaecuber.Runeguard.screen.GameScreen;
import com.github.Jaecuber.Runeguard.tiled.EntitySpawner;
import com.github.Jaecuber.Runeguard.tiled.TiledService;

public class GameViewModel extends ViewModel{
    public static final String PLAYER_DAMAGE = "playerDamage";
    public static final String HEALTH = "health";
    public static final String MAX_HEALTH = "maxHealth";
    public static final String STAMINA = "stamina";
    public static final String MAX_STAMINA = "maxStamina";
    public static final String GAME_OVER = "gameOver";
    public static final String UPGRADE = "upgrade";
    public static final String LEVEL = "level";
    public static final String WAVE = "wave";
    public static final String TIMER = "timer";
    public static final String TRANSITION = "transition";

    private Map.Entry<Vector2, Integer> playerDamage;
    private int health;
    private int maxHealth;
    private int stamina;
    private int maxStamina;
    private int level;
    private int wave;
    private int time;
    private final Vector2 tempVec2;
    private TiledService tiledService;
    private EntitySpawner entitySpawner;
    private Engine engine;

    private boolean playerUpgraded = false;

    public GameViewModel(Launcher game, TiledService tiledService, EntitySpawner entitySpawner, Engine engine){
        super(game);
        this.tempVec2 = new Vector2();
        this.tiledService = tiledService;
        this.entitySpawner = entitySpawner;
        this.engine = engine;
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
        this.game.getAudioService().playMusic(MusicAsset.GAME_OVER);
        this.propertyChangeSupport.firePropertyChange(GAME_OVER, false, true);
    }

    public void spawnEnemy(){
        int rand = (int) (Math.random() * 3) + 1;
        switch (rand) {
            case 1 -> entitySpawner.spawnEntity("green_slime", new Vector2(301.75f, 301.75f));
            case 2 -> entitySpawner.spawnEntity("undead_slime", new Vector2(301.75f, 301.75f));
            case 3 -> entitySpawner.spawnEntity("magma_slime", new Vector2(301.75f, 301.75f));
        }   
    }

    public void continueGame(){
        game.setScreen(new GameScreen(game, MapAsset.DUNGEON));
        game.getAudioService().playSound(SoundAsset.BOOM);
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

    public void updateLevel(int level){
        if(this.level != level){
            this.propertyChangeSupport.firePropertyChange(LEVEL, this.level, level);
        }
        this.level = level;
    }

    public void updateWave(int wave){
        if(this.wave != wave){
            this.propertyChangeSupport.firePropertyChange(WAVE, this.wave, wave);
        }
        playSound(SoundAsset.WAVE_HIT);
        this.playerUpgraded = false;
        this.wave = wave;
    }
    public void updateTimer(int time){
        if(this.time != time){
            this.propertyChangeSupport.firePropertyChange(TIMER, this.time, time);
        }
        this.time = time;
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

    public void playMusic(MusicAsset music){
        this.game.getAudioService().playMusic(music);
    }

    public UpgradeClass loadUpgradeClasses(){
        String raw = this.game.getAssetService().get(JsonAsset.UPGRADE_BAG);
        Json json = new Json();
        UpgradeBag upgradeBag = json.fromJson(UpgradeBag.class, raw);
        return upgradeBag.getUpgradeTypes();
    }

    public Entity getPlayerEntity(){
        ImmutableArray<Entity> entities = engine.getEntitiesFor(Family.all(UpgradeTags.class, Player.class).get());
        if(entities.size() > 0){
            return entities.first();
        }
        throw new GdxRuntimeException("No player/upgrade entity");
    }

    public void addUpgradeTag(String tag, int amt){
        Entity playerEntity = getPlayerEntity();
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(playerEntity);
        if(upgradeTags == null) throw new GdxRuntimeException("No upgrade tags for player entity");

        immediateUpgradeEffects(playerEntity, tag);

        upgradeTags.addTag(tag, amt);
        this.playerUpgraded = true;
        this.propertyChangeSupport.firePropertyChange(TRANSITION, false, true);
    }

    private void immediateUpgradeEffects(Entity playerEntity, String tag) {
        Health health = Health.MAPPER.get(playerEntity);
        switch (tag) {
            case "Soul Gamble" -> {
                if(MathUtils.randomBoolean()){
                    health.setMaxHealth(health.getMaxHealth()/2);
                    health.setHealth(Math.min(health.getHealth(), health.getMaxHealth()));
                }else{
                    health.setMaxHealth(health.getMaxHealth()*2);
                    health.setHealth(health.getHealth() * 2);
                }
            }
        }
        updateHealthInfo(health.getMaxHealth(), health.getHealth());
    }

    public Vector2 toScreenCoords(Vector2 position) {
        tempVec2.set(position);
        game.getViewport().project(tempVec2);
        return tempVec2;
    }

    public boolean hadUpgraded(){
        return this.playerUpgraded;
    }

    public void promptUpgrade() {
        this.propertyChangeSupport.firePropertyChange(UPGRADE, false, true);
    }
}
