package com.KeyforgeManagement.application.data.model;

import com.KeyforgeManagement.application.common.Utils;


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
