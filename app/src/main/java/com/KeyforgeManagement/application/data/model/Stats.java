package com.KeyforgeManagement.application.data.model;

import android.os.Parcel;

import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.HouseWinRate;
import com.KeyforgeManagement.application.data.model.wrapperDecksOfKeyforge.Percentages;

import java.io.Serializable;
import java.util.List;

public class Stats implements Serializable {
    private double averageActions;
    private double averageArtifacts;
    private double averageCreatures;
    private double averageUpgrades;
    private double averageExpectedAmber;
    private double averageAmberControl;
    private double averageCreatureControl;
    private double averageArtifactControl;
    private double averageEfficiency;
    private double averageDisruption;
    private double averageHouseCheating;
    private double averageAmberProtection;
    private double averageEffectivePower;
    private List<Percentages> sas;
    private List<Percentages> synergy;
    private List<Percentages> antisynergy;
    private List<Percentages> aerc;
    private List<Percentages> sasWinRate;
    private List<Percentages> aercWinRate;
    private List<HouseWinRate> houseWinRate;

    public double getAverageActions() {
        return averageActions;
    }

    public void setAverageActions(double averageActions) {
        this.averageActions = averageActions;
    }

    public double getAverageArtifacts() {
        return averageArtifacts;
    }

    public void setAverageArtifacts(double averageArtifacts) {
        this.averageArtifacts = averageArtifacts;
    }

    public double getAverageCreatures() {
        return averageCreatures;
    }

    public void setAverageCreatures(double averageCreatures) {
        this.averageCreatures = averageCreatures;
    }

    public double getAverageUpgrades() {
        return averageUpgrades;
    }

    public void setAverageUpgrades(double averageUpgrades) {
        this.averageUpgrades = averageUpgrades;
    }

    public double getAverageExpectedAmber() {
        return averageExpectedAmber;
    }

    public void setAverageExpectedAmber(double averageExpectedAmber) {
        this.averageExpectedAmber = averageExpectedAmber;
    }

    public double getAverageAmberControl() {
        return averageAmberControl;
    }

    public void setAverageAmberControl(double averageAmberControl) {
        this.averageAmberControl = averageAmberControl;
    }

    public double getAverageCreatureControl() {
        return averageCreatureControl;
    }

    public void setAverageCreatureControl(double averageCreatureControl) {
        this.averageCreatureControl = averageCreatureControl;
    }

    public double getAverageArtifactControl() {
        return averageArtifactControl;
    }

    public void setAverageArtifactControl(double averageArtifactControl) {
        this.averageArtifactControl = averageArtifactControl;
    }

    public double getAverageEfficiency() {
        return averageEfficiency;
    }

    public void setAverageEfficiency(double averageEfficiency) {
        this.averageEfficiency = averageEfficiency;
    }

    public double getAverageDisruption() {
        return averageDisruption;
    }

    public void setAverageDisruption(double averageDisruption) {
        this.averageDisruption = averageDisruption;
    }

    public double getAverageHouseCheating() {
        return averageHouseCheating;
    }

    public void setAverageHouseCheating(double averageHouseCheating) {
        this.averageHouseCheating = averageHouseCheating;
    }

    public double getAverageAmberProtection() {
        return averageAmberProtection;
    }

    public void setAverageAmberProtection(double averageAmberProtection) {
        this.averageAmberProtection = averageAmberProtection;
    }

    public double getAverageEffectivePower() {
        return averageEffectivePower;
    }

    public void setAverageEffectivePower(double averageEffectivePower) {
        this.averageEffectivePower = averageEffectivePower;
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
