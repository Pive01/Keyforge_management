package com.KeyforgeManagement.application.data.model;

import android.os.Parcel;

import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.HouseWinRate;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.Percentages;

import java.io.Serializable;
import java.util.List;

public class Stats implements Serializable {
    private int averageActions;
    private int averageArtifacts;
    private int averageCreatures;
    private int averageUpgrades;
    private int averageExpectedAmber;
    private int averageAmberControl;
    private int averageCreatureControl;
    private int averageArtifactControl;
    private int averageEfficiency;
    private int averageDisruption;
    private int averageHouseCheating;
    private int averageAmberProtection;
    private int averageEffectivePower;
    private List<Percentages> sas;
    private List<Percentages> synergy;
    private List<Percentages> antisynergy;
    private List<Percentages> aerc;
    private List<Percentages> sasWinRate;
    private List<Percentages> aercWinRate;
    private List<HouseWinRate> houseWinRate;

    protected Stats(Parcel in) {
        averageActions = in.readInt();
        averageArtifacts = in.readInt();
        averageCreatures = in.readInt();
        averageUpgrades = in.readInt();
        averageExpectedAmber = in.readInt();
        averageAmberControl = in.readInt();
        averageCreatureControl = in.readInt();
        averageArtifactControl = in.readInt();
        averageEfficiency = in.readInt();
        averageDisruption = in.readInt();
        averageHouseCheating = in.readInt();
        averageAmberProtection = in.readInt();
        averageEffectivePower = in.readInt();
    }

    public List<Percentages> getSynergy() {
        return synergy;
    }

    public void setSynergy(List<Percentages> synergy) {
        this.synergy = synergy;
    }

    public List<Percentages> getAntisynergy() {
        return antisynergy;
    }

    public void setAntisynergy(List<Percentages> antisynergy) {
        this.antisynergy = antisynergy;
    }

    public List<Percentages> getAerc() {
        return aerc;
    }

    public void setAerc(List<Percentages> aerc) {
        this.aerc = aerc;
    }

    public List<Percentages> getSasWinRate() {
        return sasWinRate;
    }

    public void setSasWinRate(List<Percentages> sasWinRate) {
        this.sasWinRate = sasWinRate;
    }

    public List<Percentages> getAercWinRate() {
        return aercWinRate;
    }

    public void setAercWinRate(List<Percentages> aercWinRate) {
        this.aercWinRate = aercWinRate;
    }

    public List<HouseWinRate> getHouseWinRate() {
        return houseWinRate;
    }

    public void setHouseWinRate(List<HouseWinRate> houseWinRate) {
        this.houseWinRate = houseWinRate;
    }

    public int getAverageActions() {
        return averageActions;
    }

    public void setAverageActions(int averageActions) {
        this.averageActions = averageActions;
    }

    public int getAverageArtifacts() {
        return averageArtifacts;
    }

    public void setAverageArtifacts(int averageArtifacts) {
        this.averageArtifacts = averageArtifacts;
    }

    public int getAverageCreatures() {
        return averageCreatures;
    }

    public void setAverageCreatures(int averageCreatures) {
        this.averageCreatures = averageCreatures;
    }

    public int getAverageUpgrades() {
        return averageUpgrades;
    }

    public void setAverageUpgrades(int averageUpgrades) {
        this.averageUpgrades = averageUpgrades;
    }

    public int getAverageExpectedAmber() {
        return averageExpectedAmber;
    }

    public void setAverageExpectedAmber(int averageExpectedAmber) {
        this.averageExpectedAmber = averageExpectedAmber;
    }

    public int getAverageAmberControl() {
        return averageAmberControl;
    }

    public void setAverageAmberControl(int averageAmberControl) {
        this.averageAmberControl = averageAmberControl;
    }

    public int getAverageCreatureControl() {
        return averageCreatureControl;
    }

    public void setAverageCreatureControl(int averageCreatureControl) {
        this.averageCreatureControl = averageCreatureControl;
    }

    public int getAverageArtifactControl() {
        return averageArtifactControl;
    }

    public void setAverageArtifactControl(int averageArtifactControl) {
        this.averageArtifactControl = averageArtifactControl;
    }

    public int getAverageEfficiency() {
        return averageEfficiency;
    }

    public void setAverageEfficiency(int averageEfficiency) {
        this.averageEfficiency = averageEfficiency;
    }

    public int getAverageDisruption() {
        return averageDisruption;
    }

    public void setAverageDisruption(int averageDisruption) {
        this.averageDisruption = averageDisruption;
    }

    public int getAverageHouseCheating() {
        return averageHouseCheating;
    }

    public void setAverageHouseCheating(int averageHouseCheating) {
        this.averageHouseCheating = averageHouseCheating;
    }

    public int getAverageAmberProtection() {
        return averageAmberProtection;
    }

    public void setAverageAmberProtection(int averageAmberProtection) {
        this.averageAmberProtection = averageAmberProtection;
    }

    public int getAverageEffectivePower() {
        return averageEffectivePower;
    }

    public void setAverageEffectivePower(int averageEffectivePower) {
        this.averageEffectivePower = averageEffectivePower;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "averageActions=" + averageActions +
                ", averageArtifacts=" + averageArtifacts +
                ", averageCreatures=" + averageCreatures +
                ", averageUpgrades=" + averageUpgrades +
                ", averageExpectedAmber=" + averageExpectedAmber +
                ", averageAmberControl=" + averageAmberControl +
                ", averageCreatureControl=" + averageCreatureControl +
                ", averageArtifactControl=" + averageArtifactControl +
                ", averageEfficiency=" + averageEfficiency +
                ", averageDisruption=" + averageDisruption +
                ", averageHouseCheating=" + averageHouseCheating +
                ", averageAmberProtection=" + averageAmberProtection +
                ", averageEffectivePower=" + averageEffectivePower +
                ", sas=" + sas +
                ", synergy=" + synergy +
                ", antisynergy=" + antisynergy +
                ", aerc=" + aerc +
                ", sasWinRate=" + sasWinRate +
                ", aercWinRate=" + aercWinRate +
                ", houseWinRate=" + houseWinRate +
                '}';
    }

    public List<Percentages> getSas() {
        return sas;
    }

    public void setSas(List<Percentages> sas) {
        this.sas = sas;
    }

}
