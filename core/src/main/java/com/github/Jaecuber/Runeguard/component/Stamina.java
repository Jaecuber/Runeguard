package com.github.Jaecuber.Runeguard.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.MathUtils;

public class Stamina implements Component{
    public static final ComponentMapper<Stamina> MAPPER = ComponentMapper.getFor(Stamina.class);

    private float maxStamina;
    private float stamina;
    private float staminaRegen;
    private float stamToAttack;
    private float stamToDodge;
    private float defaultAttackStam;
    private float defaultDodgeStam;
    private float defaultStamRegen;
    private float defaultMaxStam;

    public Stamina(float maxStamina, float staminaRegen, float stamToAttack, float stamToDodge){
        this.maxStamina = maxStamina;
        this.stamina = maxStamina;
        this.staminaRegen = staminaRegen;
        this.stamToAttack = stamToAttack;
        this.stamToDodge = stamToDodge;

        this.defaultAttackStam = stamToAttack;
        this.defaultDodgeStam = stamToDodge;
        this.defaultMaxStam = maxStamina;
        this.defaultStamRegen = staminaRegen;
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

    public float getStaminaToDodge() {
        return stamToDodge;
    }

    public void setStaminaToDodge(float staminaToDodge) {
        this.stamToDodge = staminaToDodge;
    }

    public float getStamToAttack() {
        return stamToAttack;
    }
    public void setStamToAttack(float stamToAttack) {
        this.stamToAttack = stamToAttack;
    }
    public float getDefaultAttackStam() {
        return defaultAttackStam;
    }
    public void setDefaultAttackStam(float defaultAttackStam) {
        this.defaultAttackStam = defaultAttackStam;
    }
    public float getDefaultDodgeStam() {
        return defaultDodgeStam;
    }
    public void setDefaultDodgeStam(float defaultDodgeStam) {
        this.defaultDodgeStam = defaultDodgeStam;
    }
    public float getDefaultStamRegen() {
        return defaultStamRegen;
    }
    public void setDefaultStamRegen(float defaultStamRegen) {
        this.defaultStamRegen = defaultStamRegen;
    }
    public float getDefaultMaxStam() {
        return defaultMaxStam;
    }
    public void setDefaultMaxStam(float defaultMaxStam) {
        this.defaultMaxStam = defaultMaxStam;
    }
}
