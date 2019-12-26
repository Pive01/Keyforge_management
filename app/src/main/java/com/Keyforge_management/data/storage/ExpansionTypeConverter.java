package com.Keyforge_management.data.storage;

import com.Keyforge_management.data.model.Expansion;

import androidx.room.TypeConverter;

public final class ExpansionTypeConverter {

    private ExpansionTypeConverter() {
    }

    @TypeConverter
    public static String fromExpansion(Expansion expansion) {
        return expansion.toString();
    }

    @TypeConverter
    public static Expansion fromString(String expansionName) {
        return Expansion.valueOf(expansionName.toUpperCase().replace(" ", "_"));
    }
}
