package com.github.Jaecuber.ui.model;

import java.util.Map;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.github.Jaecuber.Runeguard.Launcher;

public class GameViewModel extends ViewModel{
    public static final String PLAYER_DAMAGE = "playerDamage";

    private Map.Entry<Vector2, Integer> playerDamage;
    private final Vector2 tempVec2;

    public GameViewModel(Launcher game){
        super(game);
        this.tempVec2 = new Vector2();
    }

    public void playerDamage(int amount, float x, float y){
        float randomNumX = MathUtils.random(-1.0f, 1.0f);
        float randomNumY = MathUtils.random(-1.0f, 1.0f);
        Vector2 position = new Vector2(x + randomNumX,y + randomNumY);
        this.playerDamage = Map.entry(position, amount);
        this.propertyChangeSupport.firePropertyChange(PLAYER_DAMAGE, null, this.playerDamage);
    }

    public Vector2 toScreenCoords(Vector2 position) {
        tempVec2.set(position);
        game.getViewport().project(tempVec2);
        return tempVec2;
    }
}
