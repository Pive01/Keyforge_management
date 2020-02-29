package com.KeyforgeManagement.application.data.model;

import com.KeyforgeManagement.application.R;
import com.KeyforgeManagement.application.common.Utils;

import androidx.annotation.DrawableRes;


public enum Expansion {
    CALL_OF_THE_ARCHONS(R.drawable.ic_cota),
    AGE_OF_ASCENSION(R.drawable.ic_aoa),
    WORLDS_COLLIDE(R.drawable.ic_wc),
    UNKNOWN(R.drawable.ic_cota);

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
