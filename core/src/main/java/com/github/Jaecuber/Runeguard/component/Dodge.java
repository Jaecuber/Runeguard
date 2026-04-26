package com.github.Jaecuber.Runeguard.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class Dodge implements Component{
    public static final ComponentMapper<Dodge> MAPPER = ComponentMapper.getFor(Dodge.class);

    private float dodgePower;
    private float dodgeCooldown;
    private float staminaToDodge;
    private float cooldownTimer;
    private float immuneTimer;

    public Dodge(float dodgePower, float dodgeCooldown, float staminaToDodge){
        this.dodgePower = dodgePower;
        this.dodgeCooldown = dodgeCooldown;
        this.staminaToDodge = staminaToDodge;
    }

    public void startDodge(){
        this.cooldownTimer = dodgeCooldown;
        this.immuneTimer = dodgeCooldown + 0.15f;
    }

    public void tickDodgeTimer(float deltaTime){
        cooldownTimer = Math.max(0f, cooldownTimer - deltaTime);
    }

    public void tickImmuneTimer(float deltaTime){
        immuneTimer = Math.max(0f, immuneTimer - deltaTime);
    }

    public float getDodgePower() {
        return dodgePower;
    }

    public void setDodgePower(float dodgePower) {
        this.dodgePower = dodgePower;
    }

    public float getDodgeCooldown() {
        return dodgeCooldown;
    }

    public void setDodgeCooldown(float dodgeCooldown) {
        this.dodgeCooldown = dodgeCooldown;
    }

    public boolean canDodge() {
        return cooldownTimer <= 0.0f;
    }

    public float getStaminaToDodge() {
        return staminaToDodge;
    }

    public void setStaminaToDodge(float staminaToDodge) {
        this.staminaToDodge = staminaToDodge;
    }

    public boolean immune(){
        return immuneTimer > 0.0f;
    }

    public float getImmune(){
        return immuneTimer;
    }
}
