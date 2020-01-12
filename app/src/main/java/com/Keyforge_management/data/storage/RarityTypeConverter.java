package com.Keyforge_management.data.storage;


import com.Keyforge_management.data.model.Rarity;

import androidx.room.TypeConverter;

public class RarityTypeConverter {

    private RarityTypeConverter() {
    }

    @TypeConverter
    public static String fromRarity(Rarity rarity) {
        return rarity.name();
    }

    @TypeConverter
    public static Rarity fromString(String rarityName) {
        return Rarity.valueOf(rarityName);
    }
}
