<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.12.0" name="Enemies" tilewidth="64" tileheight="64" tilecount="3" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="0" type="Enemy">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="cooldown" type="float" value="2"/>
   <property name="damage" type="float" value="5"/>
   <property name="health" type="float" value="6"/>
   <property name="name" value="green_slime"/>
   <property name="speed" type="float" value="3"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="slime"/>
  </properties>
  <image source="Objects/greenSlime.png" width="64" height="64"/>
  <objectgroup draworder="index" id="2">
   <object id="8" name="detectionRadius" x="-41.5" y="-43.5" width="150" height="150">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="10" name="attackRadius" x="-1.5" y="-1.5" width="67.5" height="67.5">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="12" name="hitbox" x="28.25" y="28.625" width="7.79167" height="7.79167">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="14" name="attackHitbox" x="27" y="26.875" width="10.2917" height="10.7917">
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
 <tile id="1" type="Enemy">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="cooldown" type="float" value="2.5"/>
   <property name="damage" type="float" value="10"/>
   <property name="health" type="float" value="8"/>
   <property name="name" value="undead_slime"/>
   <property name="speed" type="float" value="6"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="aoeSlime"/>
  </properties>
  <image source="Objects/undeadSlime.png" width="64" height="64"/>
  <objectgroup draworder="index" id="2">
   <object id="1" name="detectionRadius" x="-68" y="-72" width="203" height="206">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="2" name="attackRadius" x="18.5" y="18.25" width="27.5" height="27">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="3" name="hitbox" x="28.4318" y="28.8523" width="7.79167" height="7.79167">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="5" x="23.875" y="34.8523" width="17.5" height="4.25">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
   <object id="6" name="attackHitbox" x="16.7615" y="15.8362" width="31.769" height="30.6593">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
 <tile id="3" type="Enemy">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="cooldown" type="float" value="3"/>
   <property name="damage" type="float" value="25"/>
   <property name="health" type="float" value="20"/>
   <property name="name" value="magma_slime"/>
   <property name="speed" type="float" value="2.5"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="aoeSlime"/>
  </properties>
  <image source="Objects/magmaSlime.png" width="64" height="64"/>
  <objectgroup draworder="index" id="2">
   <object id="1" name="detectionRadius" x="-68.125" y="-71.875" width="203" height="206">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="2" name="attackRadius" x="18.375" y="18.375" width="27.5" height="27">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="3" name="hitbox" x="28.3068" y="28.9773" width="7.79167" height="7.79167">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="4" x="23.75" y="34.9773" width="17.5" height="4.25">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
   <object id="6" name="attackHitbox" x="6.11549" y="5.05669" width="53.5872" height="51.7048">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
</tileset>
