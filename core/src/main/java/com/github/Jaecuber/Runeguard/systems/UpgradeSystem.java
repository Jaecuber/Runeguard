package com.github.Jaecuber.Runeguard.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.ObjectMap;
import com.github.Jaecuber.Runeguard.component.Attack;
import com.github.Jaecuber.Runeguard.component.Health;
import com.github.Jaecuber.Runeguard.component.Move;
import com.github.Jaecuber.Runeguard.component.Stamina;
import com.github.Jaecuber.Runeguard.component.UpgradeTags;

public class UpgradeSystem extends IteratingSystem{
    public UpgradeSystem(){
        super(Family.all(UpgradeTags.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        if(!upgradeTags.isDirty()) return;

        calcStats(entity);
        upgradeTags.setDirty(false);
    }

    private void calcStats(Entity entity) {
        calcDamage(entity);
        calcAttackSpeed(entity);
        calcStamina(entity);
        calcHealth(entity);
        calcMovement(entity);
    }

    private void calcMovement(Entity entity){
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        if(upgradeTags == null) return;

        Move move = Move.MAPPER.get(entity);
        ObjectMap<String, Integer> upgradesOwned = upgradeTags.getTags();

        float totalMultiplier = 1.0f;

        if(upgradesOwned.containsKey("Quick Feet")){
            totalMultiplier *= Math.pow(1.15f, upgradesOwned.get("Quick Feet"));
        }
        if(upgradesOwned.containsKey("Timewalker")){
            totalMultiplier *= Math.pow(1.40f, upgradesOwned.get("Timewalker"));
        }
        if(upgradesOwned.containsKey("Cursed Greatsword")){
            totalMultiplier *= Math.pow(0.50f, upgradesOwned.get("Cursed Greatsword"));
        }

        float finalMoveSpeed = move.getDefaultMaxSpeed() * totalMultiplier; 

        move.setMaxSpeed(finalMoveSpeed);
    }

    private void calcHealth(Entity entity){
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        if(upgradeTags == null) return;

        Health health = Health.MAPPER.get(entity);
        ObjectMap<String, Integer> upgradesOwned = upgradeTags.getTags();

        float finalMaxHealth = health.getDefaultMaxHealth();
        float finalHealthRegen = health.getDefaultRegen();

        if(upgradesOwned.containsKey("Strong Heart")){
            finalMaxHealth += 15 * upgradesOwned.get("Strong Heart");
        }
        if(upgradesOwned.containsKey("Blood Pact")){
            finalMaxHealth += 100 * upgradesOwned.get("Blood Pact");
        }
        if(upgradesOwned.containsKey("Glass Cannon")){
            finalMaxHealth /= 2;
        }
        if(upgradesOwned.containsKey("Blessing")){
            finalHealthRegen += 2 * upgradesOwned.get("Blessing");
        }
        if(upgradesOwned.containsKey("Vital Surge")){
            finalHealthRegen += 5 * upgradesOwned.get("Vital Surge");
        }
        if(upgradesOwned.containsKey("Eternal Renewal")){
            finalHealthRegen += 15 * upgradesOwned.get("Eternal Renewal");
        }
        if(upgradesOwned.containsKey("Shackles of Despair")){
            finalHealthRegen = 0;
        }

        health.setMaxHealth(finalMaxHealth);
        if(upgradesOwned.containsKey("Blood Pact")){
            health.setHealth(finalMaxHealth/2);
        }else{
            health.setHealth(finalMaxHealth);
        }
        health.setHealthRegen(finalHealthRegen);
    }

    private void calcStamina(Entity entity) {
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        if(upgradeTags == null) return;
        
        Stamina stamina = Stamina.MAPPER.get(entity);
        ObjectMap<String, Integer> upgradesOwned = upgradeTags.getTags();
        float totalMultiplier = 1.0f;  
        float finalStamRegen = stamina.getDefaultStamRegen();
        float finalMaxStam = stamina.getDefaultMaxStam();
            
        if(upgradesOwned.containsKey("Resilience")){
            totalMultiplier *= Math.pow(0.90f, upgradesOwned.get("Resilience"));
        }
        if(upgradesOwned.containsKey("Titan's Resolve")){
            totalMultiplier *= Math.pow(0.50f, upgradesOwned.get("Titan's Resolve"));
        }
        if(upgradesOwned.containsKey("Energy Flow")){
            finalStamRegen += 4 * upgradesOwned.get("Energy Flow");
        }
        if(upgradesOwned.containsKey("Resolute Steel")){
            finalMaxStam += 50 * upgradesOwned.get("Resolute Steel");
        }
        
        float finalStaminaToAttack = stamina.getDefaultAttackStam() * totalMultiplier;
        float finalStaminaToDodge = stamina.getDefaultDodgeStam() * totalMultiplier;

        stamina.setStamToAttack(finalStaminaToAttack);
        stamina.setStaminaToDodge(finalStaminaToDodge);
        stamina.setMaxStamina(finalMaxStam);
        stamina.setStaminaRegen(finalStamRegen);
    }

    private void calcAttackSpeed(Entity entity) {
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        if(upgradeTags == null) return;

        Attack attack = Attack.MAPPER.get(entity);
        ObjectMap<String, Integer> upgradesOwned = upgradeTags.getTags();
        float totalMultiplier = 1.0f;

        if(upgradesOwned.containsKey("Weapon Training")){
            totalMultiplier *= Math.pow(0.90f, upgradesOwned.get("Weapon Training"));
        }
        if(upgradesOwned.containsKey("Accelerated Strikes")){
            totalMultiplier *= Math.pow(0.75f, upgradesOwned.get("Accelerated Strikes"));
        }
        if(upgradesOwned.containsKey("Relentless Fury")){
            totalMultiplier *= Math.pow(0.50f, upgradesOwned.get("Relentless Fury"));
        }

        float finalSpeed = attack.getDefaultDelay() * totalMultiplier;
        attack.setAttackDelay(finalSpeed);
    }

    private void calcDamage(Entity entity) {
        UpgradeTags upgradeTags = UpgradeTags.MAPPER.get(entity);
        Attack attack = Attack.MAPPER.get(entity);
        float finalDamage = attack.getDefaultDamage();
        if(upgradeTags != null){
            ObjectMap<String, Integer> upgradesOwned = upgradeTags.getTags();
            if(upgradesOwned.containsKey("Sharpened Blade")){
                finalDamage += 5 * upgradesOwned.get("Sharpened Blade");
            }
            if(upgradesOwned.containsKey("Berserker's Might")){
                finalDamage += 20 * upgradesOwned.get("Berserker's Might");
            }
            if(upgradesOwned.containsKey("Wrath of the Gods")){
                finalDamage += 50 * upgradesOwned.get("Wrath of the Gods");
            }
            if(upgradesOwned.containsKey("Cursed Greatsword")){
                finalDamage += 75 * upgradesOwned.get("Cursed Greatsword");
            }
            if(upgradesOwned.containsKey("Shackles of Despair")){
                finalDamage += 80 * upgradesOwned.get("Shackles of Despair");
            }
            if(upgradesOwned.containsKey("Glass Cannon")){
                finalDamage *= Math.pow(1.5f, upgradesOwned.get("Glass Cannon"));
            }
        }
        attack.setDamage(finalDamage);
    }

}
