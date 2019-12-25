package com.Keyforge_management.data.model;

import java.io.Serializable;


public class Deck implements Serializable {
    private long id;
    private String keyforgeid;
    private String name;
    private Expansion expansion;
    private int creatureCount;
    private int actionCount;
    private int artifactCount;
    private int upgradeCount;
    private int sasRating;
    private int rawAmber;
    private House[] houses;

    public long getId() {
        return id;
    }

    public String getKeyforgeid() {
        return keyforgeid;
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
}
