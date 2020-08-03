package com.KeyforgeManagement.application.data.storage.typeConverters;

import com.KeyforgeManagement.application.data.model.Expansion;

import androidx.room.TypeConverter;

public final class ExpansionTypeConverter {

    @TypeConverter
    public static String fromExpansion(Expansion expansion) {
        return expansion.name();
    }

    @TypeConverter
    public static Expansion fromString(String expansionName) {
        Expansion toReturn = null;
        try {
            toReturn = Expansion.valueOf(expansionName);
        } catch (Exception e) {
        } finally {
            return toReturn;
        }
    }

    private ExpansionTypeConverter() {
    }
}
