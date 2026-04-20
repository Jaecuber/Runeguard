<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.10.2" name="Player" tilewidth="96" tileheight="80" tilecount="1" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="8" type="GameObject">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="attackSound" value="SWING"/>
   <property name="damage" type="float" value="2"/>
   <property name="damageDelay" type="float" value="0.5"/>
   <property name="health" type="float" value="15"/>
   <property name="regen" type="float" value="0.25"/>
   <property name="speed" type="float" value="5"/>
  </properties>
  <image width="96" height="80" source="Objects/player.png"/>
  <objectgroup draworder="index" id="2">
   <object id="1" name="player" x="39.25" y="52.75" width="17" height="6.125">
    <properties>
     <property name="categoryBits" type="int" value="2"/>
     <property name="maskBits" type="int" value="1"/>
    </properties>
    <ellipse/>
   </object>
   <object id="2" name="attack_sensor_down" x="19.3409" y="17.0227" width="50.6364" height="36.5909">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
   </object>
   <object id="4" name="attack_sensor_left" x="9.59091" y="31.5" width="42.1591" height="26">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
   </object>
   <object id="5" name="attack_sensor_right" x="33.875" y="25.5" width="49.1364" height="26">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
   </object>
   <object id="6" name="attack_sensor_up" x="19.4091" y="28.2045" width="50.6364" height="37.5">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
   </object>
   <object id="8" name="hitbox" x="45.5" y="30.375">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <polygon points="0,0 4.875,-0.125 5,7.75 6.5,9.75 6.125,19 5.125,21.5 -0.75,21.375 -2.375,10.25 -1,7.625"/>
   </object>
  </objectgroup>
 </tile>
</tileset>
