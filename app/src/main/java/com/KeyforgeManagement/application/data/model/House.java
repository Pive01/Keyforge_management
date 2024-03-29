package com.KeyforgeManagement.application.data.model;

import androidx.annotation.DrawableRes;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.common.Utils;
import com.google.gson.annotations.SerializedName;

public enum House {
    @SerializedName("Brobnar")
    BROBNAR(R.drawable.brobnar),

    @SerializedName("Shadows")
    SHADOWS(R.drawable.shadows),

    @SerializedName("Logos")
    LOGOS(R.drawable.logos),

    @SerializedName("Dis")
    DIS(R.drawable.dis),

    @SerializedName("Untamed")
    UNTAMED(R.drawable.untamed),

    @SerializedName("Sanctum")
    SANCTUM(R.drawable.sanctum),

    @SerializedName("Mars")
    MARS(R.drawable.mars),

    @SerializedName("Saurian")
    SAURIAN(R.drawable.saurian),

    @SerializedName(value = "StarAlliance", alternate = "Star Alliance")
    STAR_ALLIANCE(R.drawable.star_alliance),

    @SerializedName("Unfathomable")
    UNFATHOMABLE(R.drawable.unfathomable);

    @DrawableRes
    private final int imageId;

    House(int imageId) {
        this.imageId = imageId;
    }

    @DrawableRes
    public int getImageId() {
        return this.imageId;
    }

    @Override
    public String toString() {
        return Utils.enumValueToString(this.name());
    }
}
