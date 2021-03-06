package com.KeyforgeManagement.application.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.KeyforgeManagement.application.data.storage.typeConverters.ExpansionTypeConverter;
import com.KeyforgeManagement.application.data.storage.typeConverters.HouseArrayTypeConverter;

import java.io.Serializable;
import java.util.Arrays;


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
    private double artifactControl;
    private double creatureControl;
    private double recursion;

    public double getEfficiencyBonus() {
        return efficiencyBonus;
    }

    public void setEfficiencyBonus(double efficiencyBonus) {
        this.efficiencyBonus = efficiencyBonus;
    }

    private double efficiencyBonus;

    public double getRecursion() {
        return recursion;
    }

    public void setRecursion(double recursion) {
        this.recursion = recursion;
    }

    public int getBoardClears() {
        return boardClears;
    }

    public void setBoardClears(int boardClears) {
        this.boardClears = boardClears;
    }

    public int getScalingAemberControl() {
        return scalingAemberControl;
    }

    public void setScalingAemberControl(int scalingAemberControl) {
        this.scalingAemberControl = scalingAemberControl;
    }

    private int boardClears;
    private int scalingAemberControl;
    private double creatureProtection;
    private double efficiency;

    public double getCreatureProtection() {
        return creatureProtection;
    }

    public void setCreatureProtection(double creatureProtection) {
        this.creatureProtection = creatureProtection;
    }

    public double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }
    private double amberControl;
    private double expectedAmber;
    private double disruption;
    private int effectivePower;
    private double aercScore;
    private double synergyRating;
    private double antisynergyRating;
    private int cardDrawCount;
    private int cardArchiveCount;
    private int keyCheatCount;
    private int rawAmber;
    private int metaScores;

    public int getMetaScores() {
        return metaScores;
    }

    public void setMetaScores(int metaScores) {
        this.metaScores = metaScores;
    }

    @TypeConverters({HouseArrayTypeConverter.class})
    @ColumnInfo(name = "houses")
    private House[] houses;

    public double getAercScore() {
        return aercScore;
    }

    public void setAercScore(double aercScore) {
        this.aercScore = aercScore;
    }

    public double getSynergyRating() {
        return synergyRating;
    }

    public void setSynergyRating(double synergyRating) {
        this.synergyRating = synergyRating;
    }

    public double getAntisynergyRating() {
        return antisynergyRating;
    }

    public void setAntisynergyRating(double antisynergyRating) {
        this.antisynergyRating = antisynergyRating;
    }

    public int getCardDrawCount() {
        return cardDrawCount;
    }

    public void setCardDrawCount(int cardDrawCount) {
        this.cardDrawCount = cardDrawCount;
    }

    public int getCardArchiveCount() {
        return cardArchiveCount;
    }

    public void setCardArchiveCount(int cardArchiveCount) {
        this.cardArchiveCount = cardArchiveCount;
    }

    public int getKeyCheatCount() {
        return keyCheatCount;
    }

    public void setKeyCheatCount(int keyCheatCount) {
        this.keyCheatCount = keyCheatCount;
    }

    public double getArtifactControl() {
        return artifactControl;
    }

    public void setArtifactControl(double artifactControl) {
        this.artifactControl = artifactControl;
    }

    public double getCreatureControl() {
        return creatureControl;
    }

    public void setCreatureControl(double creatureControl) {
        this.creatureControl = creatureControl;
    }

    public double getAmberControl() {
        return amberControl;
    }

    public void setAmberControl(double amberControl) {
        this.amberControl = amberControl;
    }

    public double getExpectedAmber() {
        return expectedAmber;
    }

    public void setExpectedAmber(double expectedAmber) {
        this.expectedAmber = expectedAmber;
    }

    public double getDisruption() {
        return disruption;
    }

    public void setDisruption(double disruption) {
        this.disruption = disruption;
    }

    public int getEffectivePower() {
        return effectivePower;
    }

    public void setEffectivePower(int effectivePower) {
        this.effectivePower = effectivePower;
    }

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
                ", artifactControl=" + artifactControl +
                ", creatureControl=" + creatureControl +
                ", recursion=" + recursion +
                ", efficiencyBonus=" + efficiencyBonus +
                ", boardClears=" + boardClears +
                ", scalingAemberControl=" + scalingAemberControl +
                ", creatureProtection=" + creatureProtection +
                ", efficiency=" + efficiency +
                ", amberControl=" + amberControl +
                ", expectedAmber=" + expectedAmber +
                ", disruption=" + disruption +
                ", effectivePower=" + effectivePower +
                ", aercScore=" + aercScore +
                ", synergyRating=" + synergyRating +
                ", antisynergyRating=" + antisynergyRating +
                ", cardDrawCount=" + cardDrawCount +
                ", cardArchiveCount=" + cardArchiveCount +
                ", keyCheatCount=" + keyCheatCount +
                ", rawAmber=" + rawAmber +
                ", metaScores=" + metaScores +
                ", houses=" + Arrays.toString(houses) +
                '}';
    }
}
