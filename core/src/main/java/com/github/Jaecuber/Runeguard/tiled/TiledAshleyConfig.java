package com.github.Jaecuber.Runeguard.tiled;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.github.Jaecuber.Runeguard.Launcher;
import com.github.Jaecuber.Runeguard.asset.AssetService;
import com.github.Jaecuber.Runeguard.asset.AtlasAsset;
import com.github.Jaecuber.Runeguard.asset.SoundAsset;
import com.github.Jaecuber.Runeguard.combat.SlimeMoveset;
import com.github.Jaecuber.Runeguard.component.Animation2D;
import com.github.Jaecuber.Runeguard.component.CameraFollow;
import com.github.Jaecuber.Runeguard.component.Controller;
import com.github.Jaecuber.Runeguard.component.Enemy;
import com.github.Jaecuber.Runeguard.component.Facing;
import com.github.Jaecuber.Runeguard.component.Fsm;
import com.github.Jaecuber.Runeguard.component.Graphic;
import com.github.Jaecuber.Runeguard.component.Health;
import com.github.Jaecuber.Runeguard.component.Move;
import com.github.Jaecuber.Runeguard.component.Physics;
import com.github.Jaecuber.Runeguard.component.Player;
import com.github.Jaecuber.Runeguard.component.Transform;
import com.github.Jaecuber.Runeguard.component.Animation2D.AnimationType;
import com.github.Jaecuber.Runeguard.component.Enemy.EnemyAIState;
import com.github.Jaecuber.Runeguard.component.Attack;

public class TiledAshleyConfig {
    private static final Vector2 DEFAULT_PHYSICS_SCALING = new Vector2(1f, 1f);

    private final Engine engine;
    private final AssetService assetService;
    private final World physicsWorld;

    public TiledAshleyConfig(Engine engine, AssetService assetService, World physicsWorld) {
        this.engine = engine;
        this.assetService = assetService;
        this.physicsWorld = physicsWorld;
    }
    public void onLoadTile(TiledMapTile tileMapTile, float x, float y){
        createBody(
            tileMapTile.getObjects(),
            new Vector2(x, y),
            DEFAULT_PHYSICS_SCALING,
            BodyDef.BodyType.StaticBody,
            Vector2.Zero,
            "environment"
        );
    }

    private Body createBody(MapObjects mapObjects, Vector2 position, Vector2 scaling, 
        BodyType bodyType,Vector2 relativeTo, Object userData) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(position);
        bodyDef.fixedRotation = true;

        Body body = physicsWorld.createBody(bodyDef);
        body.setUserData(userData);
        for(MapObject object : mapObjects){
            FixtureDef fixtureDef = TiledPhysics.fixtureDefOf(object, scaling, relativeTo);
            Fixture fixture = body.createFixture(fixtureDef);
            fixture.setUserData(object.getName());
            fixtureDef.shape.dispose();
        }
        return body;
    }
    public void onLoadObject(TiledMapTileMapObject tileMapObject){
        Entity entity = this.engine.createEntity();
        TiledMapTile tile = tileMapObject.getTile();
        TextureRegion textureRegion = getTextureRegion(tile);
        int z = tile.getProperties().get("z", 1, Integer.class);

        entity.add(new Graphic(textureRegion, Color.WHITE.cpy()));
        addEntityTransform(
            tileMapObject.getX(), tileMapObject.getY(), z,
            textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), 
            tileMapObject.getScaleX(), tileMapObject.getScaleY(), 
            entity
        );
        addEntityController(tileMapObject, entity);
        addEntityMove(tile, entity);
        addEntityAnimation(tile, entity);
        BodyDef.BodyType bodyType = getObjectBodyType(tile);
        addEntityPhysics(tile.getObjects(), bodyType, Vector2.Zero, entity);
        addEntityCameraFollow(tileMapObject, entity);
        addEntityHealth(tile, entity);
        addEntityPlayer(tileMapObject, entity);
        addEntityAttack(tile, entity);
        entity.add(new Facing(Facing.FacingDirection.DOWN));

        Fsm fsm = new Fsm(entity);
        entity.add(fsm);
        addEntityEnemy(tile, entity, fsm);

        this.engine.addEntity(entity);
    }

    private void addEntityHealth(TiledMapTile tile, Entity entity) {
        float health = tile.getProperties().get("health", 0.0f, Float.class);
        if(health == 0.0f) return;

        float regen = tile.getProperties().get("regen", 0.0f, Float.class);
        entity.add(new Health(health, regen));
    }
    private void addEntityEnemy(TiledMapTile tile, Entity entity, Fsm fsm) {
        boolean enemy = tile.getProperties().get("enemy", false, Boolean.class);
        if(!enemy) return;

        String stateStr = tile.getProperties().get("state", null, String.class);
        EnemyAIState state = EnemyAIState.valueOf(stateStr);
        float speed = tile.getProperties().get("speed", 0f, Float.class);
        float cooldown = tile.getProperties().get("cooldown", 0f, Float.class);

        String type = tile.getProperties().get("type", null, String.class);

        Enemy enemyComponent = new Enemy(state, speed, cooldown);
        
        switch (type) {
            case "slime" -> enemyComponent.setMoveset(new SlimeMoveset());
        }

        fsm.initEnemyFsm(entity);
        entity.add(enemyComponent);
    }
    private void addEntityAttack(TiledMapTile tile, Entity entity) {
        float damage = tile.getProperties().get("damage", 0f, Float.class);
        if(damage==0f)return;

        float damageDelay = tile.getProperties().get("damageDelay",0f,Float.class);
        String soundAssetStr = tile.getProperties().get("attackSound", "", String.class);
        SoundAsset soundAsset = null;
        if(!soundAssetStr.isBlank()){
            soundAsset = SoundAsset.valueOf(soundAssetStr);
        }

        entity.add(new Attack(damage, damageDelay, soundAsset));
    }

    private void addEntityPlayer(TiledMapTileMapObject tileMapObject, Entity entity) {
        if ("Player".equals(tileMapObject.getName())) {
            entity.add(new Player());
        }
    }

    private void addEntityCameraFollow(TiledMapTileMapObject mapObject, Entity entity) {
        boolean camFollow = mapObject.getProperties().get("camFollow", false, Boolean.class);
        if(!camFollow) return;

        entity.add(new CameraFollow());
    }
    private BodyType getObjectBodyType(TiledMapTile tile) {
        String classType = tile.getProperties().get("type", "", String.class);
        if("Prop".equals(classType)){
            return BodyDef.BodyType.StaticBody;
        }
        return BodyDef.BodyType.DynamicBody;
    }

    private void addEntityPhysics(MapObjects objects, BodyType bodyType, Vector2 relativeTo, Entity entity) {
        if(objects.getCount() == 0) return;
        
        Transform transform = Transform.MAPPER.get(entity);
        Body body = createBody(objects, transform.getPosition(), transform.getScaling(), bodyType, relativeTo, entity);
        entity.add(new Physics(body, transform.getPosition().cpy()));
    }

    private void addEntityAnimation(TiledMapTile tile, Entity entity) {
       String animationStr = tile.getProperties().get("animation", "", String.class);
       if(animationStr.isBlank()) return;

       AnimationType animationType = AnimationType.valueOf(animationStr);
       String atlasAssetStr = tile.getProperties().get("atlasAsset", "OBJECTS", String.class);
       AtlasAsset atlasAsset = AtlasAsset.valueOf(atlasAssetStr);
       FileTextureData textureData = (FileTextureData) tile.getTextureRegion().getTexture().getTextureData();
       String atlasKey = textureData.getFileHandle().nameWithoutExtension();
       float speed = tile.getProperties().get("animationSpeed", 0f, Float.class);
       entity.add(new Animation2D(atlasAsset, atlasKey, animationType, Animation.PlayMode.LOOP, speed));
    }

    private void addEntityMove(TiledMapTile tile, Entity entity) {
        float speed = tile.getProperties().get("speed", 0f, Float.class);
        if(speed == 0f) return;

        System.out.println(speed);

        entity.add(new Move(speed));
    }

    private void addEntityController(TiledMapTileMapObject tileMapObject, Entity entity) {
        boolean controller = tileMapObject.getProperties().get("controller", false, Boolean.class);
        if(!controller) return;

        entity.add(new Controller());
    }

    private void addEntityTransform(
        float x, float y, int z,
        float w, float h,
        float scaleX, float scaleY,
        Entity entity
    ){
        Vector2 position = new Vector2(x,y);
        Vector2 size = new Vector2(w,h);
        Vector2 scaling = new Vector2(scaleX, scaleY);

        position.scl(Launcher.UNIT_SCALE);
        size.scl(Launcher.UNIT_SCALE);

        entity.add(new Transform(position, z, size, scaling, 0f));
    }

    private TextureRegion getTextureRegion(TiledMapTile tile){
        String atlastAssetStr = tile.getProperties().get("atlasAsset", AtlasAsset.OBJECTS.name(), String.class);
        AtlasAsset atlasAsset = AtlasAsset.valueOf(atlastAssetStr);
        TextureAtlas textureAtlas = this.assetService.get(atlasAsset);
        FileTextureData textureData = (FileTextureData) tile.getTextureRegion().getTexture().getTextureData();
        String atlasKey = textureData.getFileHandle().nameWithoutExtension();
        TextureAtlas.AtlasRegion region = textureAtlas.findRegion(atlasKey + "/" + atlasKey);
        if(region != null){
            return region;
        }
        
        return tile.getTextureRegion();
    }
}
