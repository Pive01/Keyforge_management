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

    private int localWins;
    private int localLosses;

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
                ", keyforgeid='" + keyforgeId + '\'' +
                ", name='" + name + '\'' +
                ", expansion=" + expansion +
                ", creatureCount=" + creatureCount +
                ", actionCount=" + actionCount +
                ", artifactCount=" + artifactCount +
                ", upgradeCount=" + upgradeCount +
                ", sasRating=" + sasRating +
                ", rawAmber=" + rawAmber +
                ", houses=" + Arrays.toString(houses) +
                '}';
    }

}
