package com.Keyforge_management.data.model;

import com.Keyforge_management.R;
import com.Keyforge_management.common.Utils;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.IdRes;

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

    @SerializedName("StarAlliance")
    STAR_ALLIANCE(R.drawable.star_alliance);

    @IdRes
    private final int imageId;

    House(int imageId) {
        this.imageId = imageId;
    }

    @IdRes
    public int getImageId() {
        return this.imageId;
    }

    @Override
    public String toString() {
        return Utils.enumValueToString(this.name());
    }
}
