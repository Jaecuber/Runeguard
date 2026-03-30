package com.github.Jaecuber.Runeguard.systems;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.Jaecuber.Runeguard.asset.AssetService;
import com.github.Jaecuber.Runeguard.asset.AtlasAsset;
import com.github.Jaecuber.Runeguard.component.Animation2D;
import com.github.Jaecuber.Runeguard.component.Facing;
import com.github.Jaecuber.Runeguard.component.Graphic;
import com.github.Jaecuber.Runeguard.component.Animation2D.AnimationType;
import com.github.Jaecuber.Runeguard.component.Facing.FacingDirection;

public class AnimationSystem extends IteratingSystem{
    private static final float FRAME_DURATION = 1/12f;

    private final AssetService assetService;
    private final Map<CacheKey, Animation<TextureRegion>> animationCache;
    
    public AnimationSystem(AssetService assetService){
        super(Family.all(Animation2D.class, Graphic.class, Facing.class).get());
        this.assetService = assetService;
        this.animationCache = new HashMap<>();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime){
        Animation2D animation2d = Animation2D.MAPPER.get(entity);
        FacingDirection facingDirection = Facing.MAPPER.get(entity).getDirection();
        final float stateTime;
        if(animation2d.isDirty() || facingDirection != animation2d.getDirection()){
            updateAnimation(animation2d, facingDirection);
            stateTime = 0f;
        }else{
            stateTime = animation2d.incAndGetStateTime(deltaTime);
        }

        Animation<TextureRegion> animation = animation2d.getAnimation();
        animation.setPlayMode(animation2d.getPlayMode());
        TextureRegion keyFrame = animation.getKeyFrame(stateTime);
        Graphic.MAPPER.get(entity).setRegion(keyFrame);
    }

    private void updateAnimation(Animation2D animation2d, FacingDirection facingDirection) {
       AtlasAsset atlasAsset = animation2d.getAtlasAsset();
       String atlasKey = animation2d.getAtlasKey();
       AnimationType type = animation2d.getType();
       CacheKey cacheKey = new CacheKey(atlasAsset, atlasKey, type, facingDirection);

       Animation<TextureRegion> animation = animationCache.computeIfAbsent(cacheKey, key ->{
           TextureAtlas textureAtlas = this.assetService.get(atlasAsset);
           String combinedKey = atlasKey + "/" + type.getAtlasKey() + "_" + facingDirection.getAtlasKey();
           Array<TextureAtlas.AtlasRegion> regions = textureAtlas.findRegions(combinedKey);
           if(regions.isEmpty()){
                throw new GdxRuntimeException("No regions found for key: " + combinedKey);
           }
           return new Animation<>(FRAME_DURATION, regions);
       });
       animation2d.setAnimation(animation, facingDirection);
    }

    public record CacheKey(AtlasAsset atlasAsset,
        String atlasKey,
        Animation2D.AnimationType type,
        Facing.FacingDirection direction) {
        
    }
}
