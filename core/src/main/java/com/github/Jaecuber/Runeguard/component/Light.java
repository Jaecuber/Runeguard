package com.github.Jaecuber.Runeguard.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

import box2dLight.PointLight;

public class Light implements Component{
    public static final ComponentMapper<Light> MAPPER = ComponentMapper.getFor(Light.class);

    private PointLight light;

    public Light(PointLight light){
        this.light = light;
    }
    
    public PointLight getLight() {
        return light;
    }
    public void setLight(PointLight light) {
        this.light = light;
    }
}
