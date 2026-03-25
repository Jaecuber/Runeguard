package com.github.Jaecuber.Runeguard.tiled;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FileTextureArrayData;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.github.Jaecuber.Runeguard.Launcher;
import com.github.Jaecuber.Runeguard.asset.AssetService;
import com.github.Jaecuber.Runeguard.asset.AtlasAsset;
import com.github.Jaecuber.Runeguard.component.Graphic;
import com.github.Jaecuber.Runeguard.component.Transform;

public class TiledAshleyConfig {
    private final Engine engine;
    private final AssetService assetService;

    public TiledAshleyConfig(Engine engine, AssetService assetService) {
        this.engine = engine;
        this.assetService = assetService;
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

        this.engine.addEntity(entity);
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
