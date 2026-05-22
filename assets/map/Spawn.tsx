<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.10" tiledversion="1.12.0" name="Spawn" tilewidth="16" tileheight="16" tilecount="1" columns="1">
 <image source="Spritesheets/Hitbox.png" width="16" height="16"/>
 <tile id="0">
  <properties>
   <property name="spawn" type="bool" value="true"/>
  </properties>
  <objectgroup draworder="index" id="2">
   <object id="1" x="0.0625" y="0.0625" width="15.875" height="15.9375">
    <properties>
     <property name="sensor" type="bool" value="true"/>
     <property name="spawn" type="bool" value="true"/>
    </properties>
   </object>
  </objectgroup>
 </tile>
</tileset>
