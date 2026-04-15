package com.github.Jaecuber.Runeguard.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.math.MathUtils;
import com.github.Jaecuber.Runeguard.asset.SoundAsset;

public class Attack implements Component{
    public static final ComponentMapper<Attack> MAPPER = ComponentMapper.getFor(Attack.class);
    private final float DEFAULT_ANIM_SPEED = 8/12f;

    private float damage;
    private float damageDelay;
    private float attackTimer;
    private SoundAsset sfx;
    private String attackAnim;

    public Attack(float damage, float damageDelay, SoundAsset sfx) {
        this.damage = damage;
        this.damageDelay = damageDelay;
        this.sfx = sfx;
        this.attackTimer = 0f;
        this.attackAnim = "anim1";
    }

    public boolean canAttack(){
        return this.attackTimer <= 0;
    }

    public boolean isAttacking(){
        return this.attackTimer > 0;
    }

    public boolean hasAttackStarted(){
        return MathUtils.isEqual(this.attackTimer, this.damageDelay, 0.0001f);
    }

    public void startAttack(){
        this.attackTimer = this.damageDelay;
    }

    public void setAttackAnim(String anim){
        attackAnim = anim;
    }

    public String getAttackAnim(){
        return attackAnim;
    }

    public void decAttackTimer(float deltaTime){
        attackTimer = Math.max(0f, attackTimer - deltaTime);
    }

    public float getDamage(){
        return damage;
    }

    public float getDamageDelay(){
        return damageDelay;
    }

    public float getDefaultAnimSpeed(){
        return DEFAULT_ANIM_SPEED;
    }

    public SoundAsset getSfx(){
        return sfx;
    }
}
