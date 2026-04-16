package com.github.Jaecuber.Runeguard.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

public class DamageListener implements Component{
    public static final ComponentMapper<DamageListener> MAPPER = ComponentMapper.getFor(DamageListener.class);
}
