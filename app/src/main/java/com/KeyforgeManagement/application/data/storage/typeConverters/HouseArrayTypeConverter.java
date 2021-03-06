package com.KeyforgeManagement.application.data.storage.typeConverters;

import com.KeyforgeManagement.application.common.Utils;
import com.KeyforgeManagement.application.data.model.House;

import androidx.room.TypeConverter;

public final class HouseArrayTypeConverter {

    private static final String DIVIDER = ",";

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

    @TypeConverter
    public static String fromHouse(House house) {
        return house.name();
    }

    @TypeConverter
    public static House fromSingleString(String houseName) {
        House toReturn = null;
        try {
            toReturn = House.valueOf(houseName);
        } catch (Exception e) {
        } finally {
            return toReturn;
        }
    }

    private HouseArrayTypeConverter() {
    }
}
