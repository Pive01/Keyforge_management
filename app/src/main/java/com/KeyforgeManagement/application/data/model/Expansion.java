package com.KeyforgeManagement.application.data.model;

import androidx.annotation.DrawableRes;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.common.Utils;


public enum Expansion {
    CALL_OF_THE_ARCHONS(R.drawable.ic_cota),
    AGE_OF_ASCENSION(R.drawable.ic_aoa),
    WORLDS_COLLIDE(R.drawable.ic_wc),
    MASS_MUTATION(R.drawable.ic_mm),
    DARK_TIDINGS(R.drawable.ic_dt);

    @DrawableRes
    private final int imageExpId;

    Expansion(int imageExpId) {
        this.imageExpId = imageExpId;
    }

    @DrawableRes
    public int getImageExpId() {
        return imageExpId;
    }
    @Override
    public String toString() {
        return Utils.enumValueToString(this.name());
    }
}
