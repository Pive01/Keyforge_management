package com.KeyforgeManagement.application.data.model.adaptation;

import com.google.gson.annotations.SerializedName;

public class Meta {
    @SerializedName("Aember Control")
    private int aemberControl;
    @SerializedName("Creature Control")
    private int creatureControl;
    @SerializedName("Artifact Control")
    private int artifactControl;
    @SerializedName("Board Clears")
    private int boardClears;
    @SerializedName("Scaling Aember Control")
    private int scalingAemberControl;

    public int getAemberControl() {
        return aemberControl;
    }

    public void setAemberControl(int aemberControl) {
        this.aemberControl = aemberControl;
    }

    public int getCreatureControl() {
        return creatureControl;
    }

    public void setCreatureControl(int creatureControl) {
        this.creatureControl = creatureControl;
    }

    public int getArtifactControl() {
        return artifactControl;
    }

    public void setArtifactControl(int artifactControl) {
        this.artifactControl = artifactControl;
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

    public int getAmount(){
        return (aemberControl +artifactControl+creatureControl+ scalingAemberControl +boardClears);
    }


}
