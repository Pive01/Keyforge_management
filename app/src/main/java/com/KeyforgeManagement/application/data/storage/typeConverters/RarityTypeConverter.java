package com.KeyforgeManagement.application.data.storage.typeConverters;


import com.KeyforgeManagement.application.data.model.Rarity;

import androidx.room.TypeConverter;

public class RarityTypeConverter {

    @TypeConverter
    public static String fromRarity(Rarity rarity) {
        return rarity.name();
    }

    @TypeConverter
    public static Rarity fromString(String rarityName) {
        return Rarity.valueOf(rarityName);
    }

    private RarityTypeConverter() {
    }
}
