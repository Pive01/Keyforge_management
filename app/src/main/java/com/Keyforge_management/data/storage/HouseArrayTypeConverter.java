package com.Keyforge_management.data.storage;

import com.Keyforge_management.common.Utils;
import com.Keyforge_management.data.model.House;

import androidx.room.TypeConverter;

public final class HouseArrayTypeConverter {

    private static final String DIVIDER = ",";

    private HouseArrayTypeConverter() {
    }

    @SuppressWarnings("unused")
    @TypeConverter
    public static String fromArray(House[] houses) {
        String[] names = new String[houses.length];
        for (int i = 0; i < houses.length; i++) {
            names[i] = houses[i].name();
        }
        return Utils.join(DIVIDER, names);
    }

    @SuppressWarnings("unused")
    @TypeConverter
    public static House[] fromString(String string) {
        String[] names = string.split(DIVIDER);
        House[] houses = new House[names.length];
        for (int i = 0; i < names.length; i++) {
            houses[i] = House.valueOf(names[i]);
        }
        return houses;
    }
}
