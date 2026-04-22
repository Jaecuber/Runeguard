package com.github.Jaecuber.Runeguard.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.MathUtils;

public class Stamina implements Component{
    public static final ComponentMapper<Stamina> MAPPER = ComponentMapper.getFor(Stamina.class);

    public float maxStamina;
    public float stamina;
    public float staminaRegen;
    public float stamToAttack;

    public Stamina(float maxStamina, float staminaRegen, float stamToAttack){
        this.maxStamina = maxStamina;
        this.stamina = maxStamina;
        this.staminaRegen = staminaRegen;
        this.stamToAttack = stamToAttack;
    }
    public boolean exhausted(){
        return stamina <= 0;
    }

    public float getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(float maxStamina) {
        this.maxStamina = maxStamina;
    }

    public float getStamina() {
        return stamina;
    }

    public void addStamina(float val) {
        this.stamina = MathUtils.clamp(stamina += val, 0f, maxStamina);
    }

    public float getStaminaRegen() {
        return staminaRegen;
    }

    public void setStaminaRegen(float staminaRegen) {
        this.staminaRegen = staminaRegen;
    }

    public float getStamToAttack() {
        return stamToAttack;
    }
    public void setStamToAttack(float stamToAttack) {
        this.stamToAttack = stamToAttack;
    }
}
