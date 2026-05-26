<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.12.0" name="Enemies" tilewidth="64" tileheight="64" tilecount="9" columns="0">
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
 <tile id="4" type="Enemy">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="cooldown" type="float" value="1"/>
   <property name="damage" type="float" value="7"/>
   <property name="health" type="float" value="8"/>
   <property name="name" value="orc"/>
   <property name="speed" type="float" value="4"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="aoeSlime"/>
  </properties>
  <image source="Objects/orc.png" width="64" height="64"/>
  <objectgroup draworder="index" id="3">
   <object id="6" name="detectionRadius" x="-68.125" y="-71.875" width="203" height="206">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="7" name="attackRadius" x="10.9205" y="10.5568" width="41.3182" height="40.8182">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="8" name="hitbox" x="24.4886" y="17.6137" width="13.7917" height="21.6099">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="9" x="22.6591" y="37.9773" width="17.5" height="4.25">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
   <object id="10" name="attackHitbox" x="5.07004" y="3.82942" width="53.5872" height="51.7048">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
 <tile id="5" type="Enemy">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="cooldown" type="float" value="0.8"/>
   <property name="damage" type="float" value="12"/>
   <property name="health" type="float" value="24"/>
   <property name="name" value="elite_orc"/>
   <property name="speed" type="float" value="4.5"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="aoeSlime"/>
  </properties>
  <image source="Objects/eliteOrc.png" width="64" height="64"/>
  <objectgroup draworder="index" id="2">
   <object id="1" name="detectionRadius" x="-75.8726" y="-77.2083" width="220.081" height="223.333">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="2" name="attackRadius" x="10.9205" y="10.5568" width="41.3182" height="40.8182">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="3" name="hitbox" x="24.4886" y="17.6137" width="13.7917" height="21.6099">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="4" x="22.6591" y="37.9773" width="17.5" height="4.25">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
   <object id="5" name="attackHitbox" x="2.7064" y="1.82942" width="59.5872" height="58.0684">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
 <tile id="6" type="Enemy">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="cooldown" type="float" value="1"/>
   <property name="damage" type="float" value="25"/>
   <property name="health" type="float" value="60"/>
   <property name="name" value="orc_warchief"/>
   <property name="speed" type="float" value="5"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="aoeSlime"/>
  </properties>
  <image source="Objects/orcWarchief.png" width="64" height="64"/>
  <objectgroup draworder="index" id="2">
   <object id="1" name="detectionRadius" x="-88.5393" y="-88.5417" width="244.081" height="247.688">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="2" name="attackRadius" x="6.4205" y="6.0568" width="50.3182" height="49.8182">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="3" name="hitbox" x="24.4886" y="17.6137" width="13.7917" height="21.6099">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="4" x="22.6591" y="37.9773" width="17.5" height="4.25">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
   <object id="5" name="attackHitbox" x="-0.2936" y="0.82942" width="64.0872" height="62.5684">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
 <tile id="7" type="Enemy">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="cooldown" type="float" value="3"/>
   <property name="damage" type="float" value="15"/>
   <property name="health" type="float" value="30"/>
   <property name="name" value="blood_servant"/>
   <property name="speed" type="float" value="4"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="aoeSlime"/>
  </properties>
  <image source="Objects/bloodServant.png" width="64" height="64"/>
  <objectgroup draworder="index" id="3">
   <object id="6" name="detectionRadius" x="-88.5393" y="-88.5417" width="244.081" height="247.688">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="7" name="attackRadius" x="-1.8295" y="0.0568" width="66.8182" height="61.8182">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="8" name="hitbox" x="24.7386" y="19.8637" width="13.7917" height="21.6099">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="9" x="22.6591" y="40.4773" width="17.5" height="4.25">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
   <object id="10" name="attackHitbox" type="Enemy" x="-0.7936" y="-0.17058" width="64.0872" height="62.5684">
    <properties>
     <property name="animation" value="IDLE"/>
     <property name="animationSpeed" type="float" value="1"/>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
 <tile id="8" type="Enemy">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="cooldown" type="float" value="3.5"/>
   <property name="damage" type="float" value="30"/>
   <property name="health" type="float" value="50"/>
   <property name="name" value="veilwalker"/>
   <property name="speed" type="float" value="4.5"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="aoeSlime"/>
  </properties>
  <image source="Objects/veilwalker.png" width="64" height="64"/>
  <objectgroup draworder="index" id="2">
   <object id="1" name="detectionRadius" x="-88.5393" y="-88.5417" width="244.081" height="247.688">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="2" name="attackRadius" x="-1.8295" y="0.0568" width="66.8182" height="61.8182">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="3" name="hitbox" x="24.7386" y="19.8637" width="13.7917" height="21.6099">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="4" x="22.6591" y="40.4773" width="17.5" height="4.25">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
   <object id="5" name="attackHitbox" type="Enemy" x="-0.7936" y="-0.17058" width="64.0872" height="62.5684">
    <properties>
     <property name="animation" value="IDLE"/>
     <property name="animationSpeed" type="float" value="1"/>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
 <tile id="9" type="Enemy">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="cooldown" type="float" value="4"/>
   <property name="damage" type="float" value="50"/>
   <property name="health" type="float" value="90"/>
   <property name="name" value="crimson_lord"/>
   <property name="speed" type="float" value="4"/>
   <property name="state" value="IDLE"/>
   <property name="type" value="aoeSlime"/>
  </properties>
  <image source="Objects/crimsonLord.png" width="64" height="64"/>
  <objectgroup draworder="index" id="3">
   <object id="6" name="detectionRadius" x="-102.073" y="-101.542" width="275.615" height="279.688">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="7" name="attackRadius" x="-1.8295" y="0.0568" width="66.8182" height="61.8182">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="8" name="hitbox" x="24.7386" y="19.8637" width="13.7917" height="21.6099">
    <properties>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
   <object id="9" x="22.6591" y="40.4773" width="17.5" height="4.25">
    <properties>
     <property name="categoryBits" type="int" value="4"/>
     <property name="maskBits" type="int" value="5"/>
    </properties>
    <ellipse/>
   </object>
   <object id="10" name="attackHitbox" type="Enemy" x="-0.7936" y="-0.17058" width="64.0872" height="62.5684">
    <properties>
     <property name="animation" value="IDLE"/>
     <property name="animationSpeed" type="float" value="1"/>
     <property name="sensor" type="bool" value="true"/>
    </properties>
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
</tileset>
