package com.KeyforgeManagement.application.data.model;

import com.KeyforgeManagement.application.R;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.DrawableRes;

public enum Rarity {

    @SerializedName(value = "Variant", alternate = "FIXED")
    SPECIAL(R.drawable.ic_special),
    @SerializedName("Common")
    COMMON(R.drawable.ic_common),
    @SerializedName("Uncommon")
    UNCOMMON(R.drawable.ic_uncommon),
    @SerializedName("Rare")
    RARE(R.drawable.ic_rare);

    @DrawableRes
    private final int imageCardId;

    Rarity(int imageCardId) {
        this.imageCardId = imageCardId;
    }

    @DrawableRes
    public int getImageCardId() {
        return imageCardId;
    }

    @Override
    public String toString() {
        return "Rarity{" +
                "imageCardId=" + imageCardId +
                '}';
    }
}
