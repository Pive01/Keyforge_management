package com.KeyforgeManagement.application.data.model.adaptation;

import com.KeyforgeManagement.application.data.model.House;
import com.KeyforgeManagement.application.data.storage.typeConverters.HouseArrayTypeConverter;

import androidx.room.TypeConverters;

public class HouseCards {
    @TypeConverters({HouseArrayTypeConverter.class})
    private House house;

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }
}
