package com.github.Jaecuber.Runeguard.data;

public class UpgradeEntry {
    private String name;
    private String rarity;
    private String description;
    public UpgradeEntry(){};
    public UpgradeEntry(String name, String rarity, String description) {
        this.name = name;
        this.rarity = rarity;
        this.description = description;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getRarity() {
        return rarity;
    }
    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
