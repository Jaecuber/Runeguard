package com.github.Jaecuber.Runeguard.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
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
import com.github.Jaecuber.Runeguard.component.Facing;
import com.github.Jaecuber.Runeguard.component.Move;
import com.github.Jaecuber.Runeguard.component.Physics;
import com.github.Jaecuber.Runeguard.component.Facing.FacingDirection;
import com.github.Jaecuber.ui.model.GameViewModel;

public class AttackSystem extends IteratingSystem{
    private static final Rectangle attackAABB = new Rectangle();

    private final AudioService audioService;
    private final World world;
    private final Vector2 tempVertex;
    private Body attackerBody;
    private GameViewModel gameViewModel;
    private float attackDamage;

    public AttackSystem(World world, AudioService audioService, GameViewModel viewModel){
        super(Family.all(Attack.class, Facing.class, Physics.class).get());
        this.audioService = audioService;
        this.world = world;
        this.tempVertex = new Vector2();
        this.attackerBody = null;
        this.gameViewModel = viewModel;
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

        //life
        //damage
        
        DamageListener damage = DamageListener.MAPPER.get(entity);
        if (damage == null) {
            entity.add(new DamageListener(this.attackDamage));
        } else {
            damage.addDamage(this.attackDamage);
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
