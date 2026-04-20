package com.github.Jaecuber.Runeguard.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.Jaecuber.Runeguard.component.Animation2D;
import com.github.Jaecuber.Runeguard.component.Enemy;
import com.github.Jaecuber.Runeguard.component.Fsm;
import com.github.Jaecuber.Runeguard.component.Move;
import com.github.Jaecuber.Runeguard.component.Physics;
import com.github.Jaecuber.Runeguard.component.Player;
import com.github.Jaecuber.Runeguard.component.Transform;

public class EnemyAiSystem extends IteratingSystem{
    private Entity playerEntity;
    private final Array<Entity> deadEntityCache = new Array<>();

    public EnemyAiSystem(){
        super(Family.all(Enemy.class, Transform.class, Fsm.class, Move.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        if (playerEntity == null) {
            ImmutableArray<Entity> players = getEngine().getEntitiesFor(
                Family.all(Player.class, Physics.class).get()
            );
            if (players.size() > 0) {
                playerEntity = players.first();
            } else {
                return;
            }
        }
        Enemy enemy = Enemy.MAPPER.get(entity);
        Physics physics = Physics.MAPPER.get(entity);
        Body body = physics.getBody();
        enemy.tickStateTimer(deltaTime);
        enemy.tickKnockbackTimer(deltaTime);

        if(enemy.isStaggered() && body != null){
            body.setLinearDamping(10f);
            return;
        }else if(!enemy.isStaggered() && body != null){
            body.setLinearDamping(0f);
            Animation2D animation2d = Animation2D.MAPPER.get(entity);
            animation2d.setSpeed(1.0f);
        }
        if(enemy.isAttacking() && body != null){
            body.setLinearDamping(10f);
        }else if(!enemy.isAttacking() && body != null){
            body.setLinearDamping(0f);
        }
        switch (enemy.getState()) {
            case IDLE -> enterIdle(entity);
            case ATTACKING -> attack(entity, deltaTime);
            case PURSUING -> pursuePlayer(entity);
            case WANDERING -> wander(entity, deltaTime);
            case DEATH -> death(entity);
            default -> throw new GdxRuntimeException("Unexpected value: " + enemy.getState());
        }
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);

        for(Entity entity : deadEntityCache){
            getEngine().removeEntity(entity);
        }
        deadEntityCache.clear();
    }

    private void death(Entity entity){
        Enemy enemy = Enemy.MAPPER.get(entity);
        if(enemy.isDead()){
            deadEntityCache.add(entity);
        }
    }

    private void enterIdle(Entity entity){
        Move move = Move.MAPPER.get(entity);
        move.setRooted(true);
    }

    private void attack(Entity entity, float deltaTime){
        if(playerEntity == null) return;
        Enemy enemy = Enemy.MAPPER.get(entity);
        Move move = Move.MAPPER.get(entity);
        move.setDirection(0, 0);
        enemy.tickAttackTimer(deltaTime);
        if(enemy.getMoveset() != null && enemy.isAttacking()){
            enemy.getMoveset().attack(entity, playerEntity);
        }
    }

    private void pursuePlayer(Entity entity){
        if (playerEntity == null) return;
        Body body = Physics.MAPPER.get(entity).getBody();
        Body playerBody = Physics.MAPPER.get(playerEntity).getBody();
        Move move = Move.MAPPER.get(entity);
        move.setRooted(false);
        Vector2 diff = new Vector2(
            playerBody.getPosition().x - body.getPosition().x,
            playerBody.getPosition().y - body.getPosition().y
        );

        if (diff.len() < 0.5f) {
            move.setDirection(0, 0);
            return;
        }

        Vector2 separation = calcSeparation(entity);
        Vector2 finalDirection = diff.nor().add(separation.scl(0.8f));

        move.setDirection(finalDirection.x, finalDirection.y);
    }

    private void wander(Entity entity, float deltaTime){
        Enemy enemy = Enemy.MAPPER.get(entity);
        Move move = Move.MAPPER.get(entity);

        move.setRooted(false);

        enemy.tickWanderTimer(deltaTime);

        if(enemy.isWanderTimerDone()){
            if(enemy.isWandering()){
                
                move.setDirection(0,0);
                enemy.setWandering(false);
                enemy.setWanderTimer(MathUtils.random(1.f,2.0f));
            }else{
                move.setDirection(MathUtils.random(-1f, 1f), MathUtils.random(-0.5f, 0.5f));
                enemy.setWandering(true);
                enemy.setWanderTimer(MathUtils.random(1.0f,2.0f));
            }
        }
    }

    private Vector2 calcSeparation(Entity entity){
        Body body = Physics.MAPPER.get(entity).getBody();
        Vector2 separation = new Vector2();
        float separationRad = 2.0f;

        for(Entity other : getEntities()){
            if(other == entity) continue;

            Body otherBody = Physics.MAPPER.get(other).getBody();
            Vector2 diff = new Vector2(
                body.getPosition().x - otherBody.getPosition().x,
                body.getPosition().y - otherBody.getPosition().y
            );

            float distance = diff.len();
            if (distance < separationRad && distance > 0) {
                separation.add(diff.nor().scl(separationRad - distance));
            }
        }
        return separation;
    }


}
