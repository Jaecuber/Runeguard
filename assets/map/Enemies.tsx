<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.10.2" name="Enemies" tilewidth="64" tileheight="64" tilecount="1" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0" type="Enemy">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="cooldown" type="float" value="2"/>
   <property name="damage" type="float" value="2"/>
   <property name="health" type="float" value="6"/>
   <property name="speed" type="float" value="3"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="slime"/>
  </properties>
  <image width="64" height="64" source="Objects/greenSlime.png"/>
  <objectgroup draworder="index" id="2">
   <object id="8" name="detectionRadius" x="-41.5" y="-43.5" width="150" height="150">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="10" name="attackRadius" x="-2.5" y="-3" width="67.5" height="67.5">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="12" name="hitbox" x="28.5" y="28.875" width="7.79167" height="7.79167">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="13" x="23.875" y="34.625" width="17.5" height="4.25">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
</tileset>
