package com.Keyforge_management;

import android.text.TextUtils;

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
    final int imageId;

    House(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        String[] tokens = this.name().split("_");
        String[] nameTokens = new String[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            nameTokens[i] = tokens[i].charAt(0) + tokens[i].substring(1).toLowerCase();
        }
        return TextUtils.join(" ", nameTokens);
    }
}
