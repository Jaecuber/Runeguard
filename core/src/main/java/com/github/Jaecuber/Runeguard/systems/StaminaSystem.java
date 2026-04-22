package com.github.Jaecuber.Runeguard.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.Runeguard.component.Health;
import com.github.Jaecuber.Runeguard.component.Player;
import com.github.Jaecuber.Runeguard.component.Stamina;
import com.github.Jaecuber.ui.model.GameViewModel;

public class StaminaSystem extends IteratingSystem implements EntityListener{
    private final GameViewModel viewModel;

    public StaminaSystem(GameViewModel viewModel){
        super(Family.all(Stamina.class).get());
        this.viewModel = viewModel;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Stamina stamina = Stamina.MAPPER.get(entity);
        if(stamina.getStamina() == stamina.getMaxStamina()) return;

        stamina.addStamina(stamina.getStaminaRegen() * deltaTime);
        if (Player.MAPPER.get(entity) != null) {
            viewModel.updateStaminaInfo(stamina.getMaxStamina(), stamina.getStamina());
        }
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        engine.addEntityListener(Family.all(Stamina.class, Player.class).get(), this);
    }

    @Override
    public void removedFromEngine(Engine engine) {
        super.removedFromEngine(engine);
        engine.removeEntityListener(this);
    }

    @Override
    public void entityAdded(Entity entity) {
        Stamina stamina = Stamina.MAPPER.get(entity);
        viewModel.updateStaminaInfo(stamina.getMaxStamina(), stamina.getStamina());
    }

    @Override
    public void entityRemoved(Entity entity) {
    }
}
