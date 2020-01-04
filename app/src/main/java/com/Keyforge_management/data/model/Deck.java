package com.Keyforge_management.data.model;

import com.Keyforge_management.data.storage.ExpansionTypeConverter;
import com.Keyforge_management.data.storage.HouseArrayTypeConverter;

import java.io.Serializable;
import java.util.Arrays;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "decks")
public class Deck implements Serializable {
    @PrimaryKey
    private long id;
    private String keyforgeId;
    private String name;
    @TypeConverters({ExpansionTypeConverter.class})
    private Expansion expansion;
    private int creatureCount;
    private int actionCount;
    private int artifactCount;
    private int upgradeCount;
    private int sasRating;
    private int powerLevel;
    private int chains;
    private int wins;
    private int losses;
    private int totalPower;
    private int totalArmor;
    private int localWins;
    private int localLosses;

    public int getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    public int getChains() {
        return chains;
    }

    public void setChains(int chains) {
        this.chains = chains;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTotalPower() {
        return totalPower;
    }

    public void setTotalPower(int totalPower) {
        this.totalPower = totalPower;
    }

    public int getTotalArmor() {
        return totalArmor;
    }

    public void setTotalArmor(int totalArmor) {
        this.totalArmor = totalArmor;
    }



    public int getLocalWins() {
        return localWins;
    }

    public void setLocalWins(int localWins) {
        this.localWins = localWins;
    }

    public int getLocalLosses() {
        return localLosses;
    }

    public void setLocalLosses(int localLosses) {
        this.localLosses = localLosses;
    }

    private int rawAmber;
    @TypeConverters({HouseArrayTypeConverter.class})
    private House[] houses;

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyforgeId() {
        return keyforgeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpansion(Expansion expansion) {
        this.expansion = expansion;
    }

    public void setCreatureCount(int creatureCount) {
        this.creatureCount = creatureCount;
    }

    public void setActionCount(int actionCount) {
        this.actionCount = actionCount;
    }

    public void setArtifactCount(int artifactCount) {
        this.artifactCount = artifactCount;
    }

    public void setUpgradeCount(int upgradeCount) {
        this.upgradeCount = upgradeCount;
    }

    public void setSasRating(int sasRating) {
        this.sasRating = sasRating;
    }

    public void setRawAmber(int rawAmber) {
        this.rawAmber = rawAmber;
    }

    public void setHouses(House[] houses) {
        this.houses = houses;
    }

    public long getId() {
        return id;
    }

    public void setKeyforgeId(String keyforgeId) {
        this.keyforgeId = keyforgeId;
    }

    public String getName() {
        return name;
    }

    public int getCreatureCount() {
        return creatureCount;
    }

    public int getActionCount() {
        return actionCount;
    }

    public int getArtifactCount() {
        return artifactCount;
    }

    public int getUpgradeCount() {
        return upgradeCount;
    }

    public int getSasRating() {
        return sasRating;
    }

    public int getRawAmber() {
        return rawAmber;
    }

    public House[] getHouses() {
        return houses;
    }

    public Expansion getExpansion() {
        return expansion;
    }


    @Override
    public String toString() {
        return "Deck{" +
                "id=" + id +
                ", keyforgeId='" + keyforgeId + '\'' +
                ", name='" + name + '\'' +
                ", expansion=" + expansion +
                ", creatureCount=" + creatureCount +
                ", actionCount=" + actionCount +
                ", artifactCount=" + artifactCount +
                ", upgradeCount=" + upgradeCount +
                ", sasRating=" + sasRating +
                ", powerLevel=" + powerLevel +
                ", chains=" + chains +
                ", wins=" + wins +
                ", losses=" + losses +
                ", totalPower=" + totalPower +
                ", totalArmor=" + totalArmor +
                ", localWins=" + localWins +
                ", localLosses=" + localLosses +
                ", rawAmber=" + rawAmber +
                ", houses=" + Arrays.toString(houses) +
                '}';
    }
}
