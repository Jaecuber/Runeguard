package com.github.Jaecuber.Runeguard.systems;

import java.util.concurrent.TransferQueue;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.Runeguard.component.DamageListener;
import com.github.Jaecuber.Runeguard.component.Transform;
import com.github.Jaecuber.ui.model.GameViewModel;

public class DamageSystem extends IteratingSystem{
    private final GameViewModel viewModel;

    public DamageSystem(GameViewModel viewModel){
        super(Family.all(DamageListener.class).get());
        this.viewModel = viewModel;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        DamageListener damage = DamageListener.MAPPER.get(entity);
        entity.remove(DamageListener.class);

        Transform transform = Transform.MAPPER.get(entity);
        if(transform != null){
            float x = transform.getPosition().x + transform.getSize().x * 0.5f;
            float y = transform.getPosition().y;
            viewModel.playerDamage((int) damage.getDamage(), x, y);
        }
    }
}
