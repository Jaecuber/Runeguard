package com.github.Jaecuber.Runeguard.data;

import com.badlogic.gdx.utils.Array;

public class UpgradeClass {
    private Array<UpgradeEntry> basicUpgrades = new Array<>();
    private Array<UpgradeEntry> rareUpgrades = new Array<>();
    private Array<UpgradeEntry> legendaryUpgrades = new Array<>();
    private Array<UpgradeEntry> cursedUpgrades = new Array<>();

    public UpgradeClass(){};
    public UpgradeClass(Array<UpgradeEntry> basicUpgrades, Array<UpgradeEntry> rareUpgrades, Array<UpgradeEntry> legendaryUpgrades, Array<UpgradeEntry> cursedUpgrades){
        this.basicUpgrades = basicUpgrades;
        this.rareUpgrades = rareUpgrades;
        this.legendaryUpgrades = legendaryUpgrades;
        this.cursedUpgrades = cursedUpgrades;
    }
    public Array<UpgradeEntry> getBasicUpgrades() {
        return basicUpgrades;
    }
    public void setBasicUpgrades(Array<UpgradeEntry> basicUpgrades) {
        this.basicUpgrades = basicUpgrades;
    }
    public Array<UpgradeEntry> getRareUpgrades() {
        return rareUpgrades;
    }
    public void setRareUpgrades(Array<UpgradeEntry> rareUpgrades) {
        this.rareUpgrades = rareUpgrades;
    }
    public Array<UpgradeEntry> getLegendaryUpgrades() {
        return legendaryUpgrades;
    }
    public void setLegendaryUpgrades(Array<UpgradeEntry> legendaryUpgrades) {
        this.legendaryUpgrades = legendaryUpgrades;
    }
    public Array<UpgradeEntry> getCursedUpgrades() {
        return cursedUpgrades;
    }
    public void setCursedUpgrades(Array<UpgradeEntry> cursedUpgrades) {
        this.cursedUpgrades = cursedUpgrades;
    }
}
