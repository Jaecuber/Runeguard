package com.github.Jaecuber.Runeguard.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.fsm.StackStateMachine;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.Jaecuber.Runeguard.audio.AudioService;
import com.github.Jaecuber.Runeguard.component.Animation2D;
import com.github.Jaecuber.Runeguard.component.Attack;
import com.github.Jaecuber.Runeguard.component.DamageListener;
import com.github.Jaecuber.Runeguard.component.Enemy;
import com.github.Jaecuber.Runeguard.component.Facing;
import com.github.Jaecuber.Runeguard.component.Move;
import com.github.Jaecuber.Runeguard.component.Physics;
import com.github.Jaecuber.Runeguard.component.Stamina;
import com.github.Jaecuber.Runeguard.component.Facing.FacingDirection;

public class AttackSystem extends IteratingSystem{
    private static final Rectangle attackAABB = new Rectangle();

    private final AudioService audioService;
    private final World world;
    private final Vector2 tempVertex;
    private Body attackerBody;
    private float attackDamage;

    public AttackSystem(World world, AudioService audioService){
        super(Family.all(Attack.class, Facing.class, Physics.class).get());
        this.audioService = audioService;
        this.world = world;
        this.tempVertex = new Vector2();
        this.attackerBody = null;
        this.attackDamage = 0f;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Attack attack = Attack.MAPPER.get(entity);
        Animation2D animation = Animation2D.MAPPER.get(entity);
        float animSpeed = (attack.getDefaultAnimSpeed()/attack.getDamageDelay()) + 0.025f;

        if(attack.canAttack()) return;

        if(attack.hasAttackStarted() && attack.getSfx() != null){
            audioService.playSound(attack.getSfx());
            Move move = Move.MAPPER.get(entity);
            animation.setSpeed(animSpeed);
            if(move != null){
                move.setRooted(true);
            }
            if(attack.getAttackAnim().equals("attack1")){
                attack.setAttackAnim("attack2");
            }else{
                attack.setAttackAnim("attack1");
            }
        }

        attack.decAttackTimer(deltaTime);

        if(attack.canDamage()){
            FacingDirection facingDirection = Facing.MAPPER.get(entity).getDirection();
            attackerBody = Physics.MAPPER.get(entity).getBody();
            PolygonShape attackShape = getAttackFixture(attackerBody, facingDirection);
            updateAttackAABB(attackerBody.getPosition(), attackShape);
            this.attackDamage = attack.getDamage();
            world.QueryAABB(this::attackCallback, attackAABB.x, attackAABB.y, attackAABB.width, attackAABB.height);
        }
        
        if(attack.canAttack()){
            animation.setSpeed(1.0f);
            Move move = Move.MAPPER.get(entity);
            if(move != null){
                move.setRooted(false);
            }
        }
    }

    private void updateAttackAABB(Vector2 bodyPosition, PolygonShape attackShape) {
        attackShape.getVertex(0, tempVertex);
        tempVertex.add(bodyPosition);
        attackAABB.setPosition(tempVertex.x, tempVertex.y);

        attackShape.getVertex(2, tempVertex);
        tempVertex.add(bodyPosition);
        attackAABB.setSize(tempVertex.x, tempVertex.y);
    }

    private boolean attackCallback(Fixture fixture){
        Body body = fixture.getBody();
        if(body.equals(attackerBody)) return true;
        if(!(body.getUserData() instanceof Entity entity)) return true;
        if(fixture.getUserData() == null) return true;
        if(!fixture.getUserData().equals("hitbox"))return true;
        //knockback
        Enemy enemy = Enemy.MAPPER.get(entity);
        if(!enemy.isDead()){
            enemy.applyKnockback(0.3f);

            Vector2 knockbackDirection = new Vector2(
                body.getPosition().x - attackerBody.getPosition().x,
                body.getPosition().y - attackerBody.getPosition().y
            ).nor().scl(20f);
            body.applyLinearImpulse(knockbackDirection, body.getWorldCenter(), true);
        }
        //hurt animation
        float animSpeed = (5/12f) / (0.3f);
        Animation2D animation2d = Animation2D.MAPPER.get(entity);
        animation2d.setSpeed(animSpeed);
        //damage 
        DamageListener damage = DamageListener.MAPPER.get(entity);
        if (damage == null) {
            entity.add(new DamageListener(this.attackDamage));
        } else {
            damage.addDamage(this.attackDamage);
        }
        //stamina
        Stamina stamina = Stamina.MAPPER.get(entity);
        if(stamina != null){
            stamina.addStamina(-20.0f);
        }
        return true;
    }

    private PolygonShape getAttackFixture(Body body, FacingDirection facingDirection) {
        Array<Fixture> fixtureList = body.getFixtureList();
        String fixtureName = "attack_sensor_" + facingDirection.getAtlasKey();
        for(Fixture fixture : fixtureList){
            if(fixtureName.equals(fixture.getUserData()) && Shape.Type.Polygon.equals(fixture.getShape().getType())){
                return (PolygonShape) fixture.getShape();
            }
        }

        throw new GdxRuntimeException("Entity has no attack sensor of name " + fixtureName);
    }
}
