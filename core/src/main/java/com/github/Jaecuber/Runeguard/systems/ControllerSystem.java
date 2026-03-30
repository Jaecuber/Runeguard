package com.github.Jaecuber.Runeguard.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.Jaecuber.Runeguard.asset.SoundAsset;
import com.github.Jaecuber.Runeguard.audio.AudioService;
import com.github.Jaecuber.Runeguard.component.Controller;
import com.github.Jaecuber.Runeguard.component.Move;
import com.github.Jaecuber.Runeguard.input.Command;

public class ControllerSystem extends IteratingSystem{

    private final AudioService audioService;

    public ControllerSystem(AudioService audioService){
        super(Family.all(Controller.class).get());
        this.audioService = audioService; 
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime){
        Controller controller = Controller.MAPPER.get(entity);
        if(controller.getPressedCommands().isEmpty() && controller.getReleasedCommands().isEmpty()){
            return;
        }
        for(Command command : controller.getPressedCommands()){
            switch (command) {
                case UP -> moveEntity(entity, 0f, 1f);
                case DOWN -> moveEntity(entity, 0f, -1f);
                case LEFT -> moveEntity(entity, -1f, 0f);
                case RIGHT -> moveEntity(entity, 1f, 0f);
                case SELECT -> startEntityAttack(entity);
            }
        }
        controller.getPressedCommands().clear();
        for(Command command : controller.getReleasedCommands()){
            switch (command) {
                case UP -> moveEntity(entity, 0f, -1f);
                case DOWN -> moveEntity(entity, 0f, 1f);
                case LEFT -> moveEntity(entity, 1f, 0f);
                case RIGHT -> moveEntity(entity, -1f, 0f);
            }
        }
        controller.getReleasedCommands().clear();
    }

    private void startEntityAttack(Entity entity) {
        audioService.playSound(SoundAsset.SWORD_HIT);
    }

    private void moveEntity(Entity entity, float directionX, float directionY){
        Move move = Move.MAPPER.get(entity);
        if(move == null) return;

        move.getDirection().x += directionX;
        move.getDirection().y += directionY;
    }
}
