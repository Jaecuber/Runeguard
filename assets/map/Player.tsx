<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.12.0" name="Player" tilewidth="22" tileheight="34" tilecount="1" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="1" x="37" y="24" width="22" height="34" type="GameObject">
  <properties>
   <property name="animation" value="IDLE"/>
   <property name="animationSpeed" type="float" value="1"/>
   <property name="atlasAsset" value="OBJECTS"/>
   <property name="speed" type="float" value="8"/>
  </properties>
  <image source="Objects/player.png" width="96" height="80"/>
  <objectgroup draworder="index" id="2">
   <object id="1" x="47" y="54" width="0.25">
    <ellipse/>
   </object>
   <object id="2" x="2.3447" y="27.3409" width="17.0909" height="7.45455">
    <ellipse/>
   </object>
  </objectgroup>
 </tile>
</tileset>
