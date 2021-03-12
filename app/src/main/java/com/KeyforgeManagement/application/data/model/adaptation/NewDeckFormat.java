package com.KeyforgeManagement.application.data.model.adaptation;

import androidx.room.TypeConverters;

import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.Expansion;
import com.KeyforgeManagement.application.data.model.House;
import com.KeyforgeManagement.application.data.storage.typeConverters.ExpansionTypeConverter;
import com.KeyforgeManagement.application.data.storage.typeConverters.HouseArrayTypeConverter;

import java.util.Arrays;

public class NewDeckFormat {
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
    private Meta metaScores;
    private int losses;
    private int totalPower;
    private int totalArmor;
    private int localWins;
    private int localLosses;
    private double efficiency;
    private double creatureProtection;

    private double amberControl;

    public double getAmberControl() {
        return amberControl;
    }

    public void setAmberControl(double amberControl) {
        this.amberControl = amberControl;
    }

    public double getCreatureControl() {
        return creatureControl;
    }

    public void setCreatureControl(double creatureControl) {
        this.creatureControl = creatureControl;
    }

    public double getArtifactControl() {
        return artifactControl;
    }

    public void setArtifactControl(double artifactControl) {
        this.artifactControl = artifactControl;
    }

    public double getRecursion() {
        return recursion;
    }

    public void setRecursion(double recursion) {
        this.recursion = recursion;
    }

    private double creatureControl;
    private double artifactControl;
    private double recursion;

    public double getEfficiency() {
        return efficiency;
    }

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

    public double getEfficiencyBonus() {
        return efficiencyBonus;
    }

    public void setEfficiencyBonus(double efficiencyBonus) {
        this.efficiencyBonus = efficiencyBonus;
    }

    private double efficiencyBonus;
    @TypeConverters({HouseArrayTypeConverter.class})
    private HouseCards[] housesAndCards;

    public Meta getMetaScores() {
        return metaScores;
    }

    public void setMetaScores(Meta metaScores) {
        this.metaScores = metaScores;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyforgeId() {
        return keyforgeId;
    }

    public void setKeyforgeId(String keyforgeId) {
        this.keyforgeId = keyforgeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Expansion getExpansion() {
        return expansion;
    }

    public void setExpansion(Expansion expansion) {
        this.expansion = expansion;
    }

    public int getCreatureCount() {
        return creatureCount;
    }

    public void setCreatureCount(int creatureCount) {
        this.creatureCount = creatureCount;
    }

    public int getActionCount() {
        return actionCount;
    }

    public void setActionCount(int actionCount) {
        this.actionCount = actionCount;
    }

    public int getArtifactCount() {
        return artifactCount;
    }

    public void setArtifactCount(int artifactCount) {
        this.artifactCount = artifactCount;
    }

    public int getUpgradeCount() {
        return upgradeCount;
    }

    public void setUpgradeCount(int upgradeCount) {
        this.upgradeCount = upgradeCount;
    }

    public int getSasRating() {
        return sasRating;
    }

    public void setSasRating(int sasRating) {
        this.sasRating = sasRating;
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

    public int getRawAmber() {
        return rawAmber;
    }

    public void setRawAmber(int rawAmber) {
        this.rawAmber = rawAmber;
    }

    public HouseCards[] getHousesAndCards() {
        return housesAndCards;
    }

    public void setHousesAndCards(HouseCards[] housesAndCards) {
        this.housesAndCards = housesAndCards;
    }

    private House[] ExtractHouses() {
        House[] arr = new House[3];
        arr[0] = this.getHousesAndCards()[0].getHouse();
        arr[1] = this.getHousesAndCards()[1].getHouse();
        arr[2] = this.getHousesAndCards()[2].getHouse();
        return arr;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = efficiency;
    }

    public double getCreatureProtection() {
        return creatureProtection;
    }

    public void setCreatureProtection(double creatureProtection) {
        this.creatureProtection = creatureProtection;
    }

    public Deck convertToOld() {
        Deck converted = new Deck();
        converted.setId(this.getId());
        converted.setKeyforgeId(this.getKeyforgeId());
        converted.setName(this.getName());
        converted.setExpansion(this.getExpansion());
        converted.setCreatureCount(this.getCreatureCount());
        converted.setActionCount(this.getActionCount());
        converted.setArtifactCount(this.getArtifactCount());
        converted.setUpgradeCount(this.getUpgradeCount());
        converted.setSasRating(this.getSasRating());
        converted.setPowerLevel(this.getPowerLevel());
        converted.setChains(this.getChains());
        converted.setWins(this.getWins());
        converted.setLosses(this.getLosses());
        converted.setTotalPower(this.getTotalPower());
        converted.setTotalArmor(this.getTotalArmor());
        converted.setLocalWins(this.getLocalWins());
        converted.setLocalLosses(this.getLocalLosses());
        converted.setArtifactControl(this.getArtifactControl());
        converted.setCreatureControl(this.getCreatureControl());
        converted.setRecursion(this.getRecursion());
        converted.setEfficiencyBonus(this.getEfficiencyBonus());
        converted.setAmberControl(this.getAmberControl());
        converted.setExpectedAmber(this.getExpectedAmber());
        converted.setDisruption(this.getDisruption());
        converted.setEffectivePower(this.getEffectivePower());
        converted.setAercScore(this.getAercScore());
        converted.setSynergyRating(this.getSynergyRating());
        converted.setAntisynergyRating(this.getAntisynergyRating());
        converted.setCardDrawCount(this.getCardDrawCount());
        converted.setCardArchiveCount(this.getCardArchiveCount());
        converted.setKeyCheatCount(this.getKeyCheatCount());
        converted.setRawAmber(this.getRawAmber());
        converted.setHouses(this.ExtractHouses());
        converted.setEfficiency(this.getEfficiency());
        converted.setCreatureProtection(this.getCreatureProtection());
        converted.setBoardClears(this.getMetaScores().getBoardClears());
        converted.setScalingAemberControl(this.getMetaScores().getScalingAemberControl());
        converted.setMetaScores(this.getMetaScores().getAmount());
        return converted;
    }

    @Override
    public String toString() {
        return "NewDeckFormat{" +
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
                ", metaScores=" + metaScores +
                ", losses=" + losses +
                ", totalPower=" + totalPower +
                ", totalArmor=" + totalArmor +
                ", localWins=" + localWins +
                ", localLosses=" + localLosses +
                ", efficiency=" + efficiency +
                ", creatureProtection=" + creatureProtection +
                ", amberControl=" + amberControl +
                ", creatureControl=" + creatureControl +
                ", artifactControl=" + artifactControl +
                ", recursion=" + recursion +
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
                ", efficiencyBonus=" + efficiencyBonus +
                ", housesAndCards=" + Arrays.toString(housesAndCards) +
                '}';
    }
}
