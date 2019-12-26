package com.Keyforge_management.data.model;

import com.Keyforge_management.common.Utils;


public enum Expansion {
    CALL_OF_THE_ARCHONS,
    AGE_OF_ASCENSION,
    WORLDS_COLLIDE,
    UNKNOWN;

    @Override
    public String toString() {
        return Utils.enumValueToString(this.name());
    }
}
