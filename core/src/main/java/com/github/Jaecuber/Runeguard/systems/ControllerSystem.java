package com.github.Jaecuber.Runeguard.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.Jaecuber.Runeguard.component.Attack;
import com.github.Jaecuber.Runeguard.component.Controller;
import com.github.Jaecuber.Runeguard.component.Dodge;
import com.github.Jaecuber.Runeguard.component.Facing;
import com.github.Jaecuber.Runeguard.component.Move;
import com.github.Jaecuber.Runeguard.component.Physics;
import com.github.Jaecuber.Runeguard.component.Stamina;
import com.github.Jaecuber.Runeguard.component.Facing.FacingDirection;
import com.github.Jaecuber.Runeguard.input.Command;

public class ControllerSystem extends IteratingSystem{
    public ControllerSystem(){
        super(Family.all(Controller.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime){
        Controller controller = Controller.MAPPER.get(entity);
        Move move = Move.MAPPER.get(entity);
        Body body = Physics.MAPPER.get(entity).getBody();
        Dodge dodge = Dodge.MAPPER.get(entity);
        if(controller.getPressedCommands().isEmpty() && controller.getReleasedCommands().isEmpty() && move == null){
            return;
        }
        if(body == null) return;
        if(move.isDoingAction() && body != null){
            body.setLinearDamping(10f);
        }else if (!move.isDoingAction() && body != null){
            body.setLinearDamping(0f);
        }

        for(Command command : controller.getPressedCommands()){
            switch (command) {
                case UP -> moveEntity(entity, 0f, 1f);
                case DOWN -> moveEntity(entity, 0f, -1f);
                case LEFT -> moveEntity(entity, -1f, 0f);
                case RIGHT -> moveEntity(entity, 1f, 0f);
                case SELECT -> startEntityAttack(entity);
                case DODGE -> entityDodge(entity, move.getDirection().x, move.getDirection().y);

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
        Attack attack = Attack.MAPPER.get(entity);
        Stamina stamina = Stamina.MAPPER.get(entity);
        if(attack != null && attack.canAttack() && stamina.getStamina() > stamina.getStamToAttack()){
            attack.startAttack();
        }
    }

    private void entityDodge(Entity entity, float directionX, float directionY){
        Facing facing = Facing.MAPPER.get(entity);
        FacingDirection facingDir = null;
        Stamina stamina = Stamina.MAPPER.get(entity);
        if(facing == null) return;

        Move move = Move.MAPPER.get(entity);
        move.setDoingAction(true);
        Dodge dodge = Dodge.MAPPER.get(entity);

        if(dodge.canDodge() && stamina.getStamina() > stamina.getStamToAttack() && dodge != null){
            dodge.startDodge();
            stamina.addStamina(-dodge.getStaminaToDodge());
            if(directionX == 0 && directionY == 0){
                facingDir = facing.getDirection();
                switch (facingDir) {
                    case UP -> applyDodgeImpulseVector(entity, 0f, 1f);
                    case DOWN -> applyDodgeImpulseVector(entity, 0f, -1f);
                    case LEFT -> applyDodgeImpulseVector(entity, -1f, 0f);
                    case RIGHT -> applyDodgeImpulseVector(entity, 1f, 0f);
                }
            }else{
                applyDodgeImpulseVector(entity, directionX, directionY);
            }
        }
    }

    private void applyDodgeImpulseVector(Entity entity, float directionX, float directionY){
        Body body = Physics.MAPPER.get(entity).getBody();
        Dodge dodge = Dodge.MAPPER.get(entity);
        if(dodge == null) return;
        if(body == null) return;

        Vector2 dodgeVector = new Vector2(directionX, directionY).nor().scl(dodge.getDodgePower());
        body.applyLinearImpulse(dodgeVector, body.getWorldCenter(), true);
    }

    private void moveEntity(Entity entity, float directionX, float directionY){
        Move move = Move.MAPPER.get(entity);
        if(move == null) return;

        move.getDirection().x += directionX;
        move.getDirection().y += directionY;
    }
}
