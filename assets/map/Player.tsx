<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.12.0" name="Player" tilewidth="96" tileheight="80" tilecount="1" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="8" type="GameObject">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="attackSound" value="SWING"/>
   <property name="damage" type="float" value="4"/>
   <property name="damageDelay" type="float" value="0.5"/>
   <property name="health" type="float" value="15"/>
   <property name="regen" type="float" value="0.25"/>
   <property name="speed" type="float" value="6"/>
  </properties>
  <image source="Objects/player.png" width="96" height="80"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="39.25" y="52.75" width="17" height="6.125">
    <ellipse/>
   </object>
   <object id="2" name="attack_sensor_down" x="18.25" y="31.75" width="55" height="39.5">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
   </object>
   <object id="4" name="attack_sensor_left" x="6.5" y="31.5" width="45.25" height="26">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
   </object>
   <object id="5" name="attack_sensor_right" x="33.875" y="25.5" width="53.5" height="26">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
   </object>
   <object id="6" name="attack_sensor_up" x="18.5" y="16.75" width="55" height="39.5">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
   </object>
  </objectgroup>
 </tile>
</tileset>
