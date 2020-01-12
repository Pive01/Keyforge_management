package com.Keyforge_management.data.storage.typeConverters;

import com.Keyforge_management.data.model.House;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class HouseArrayTypeConverterTest {

    @Test
    public void fromArray() {
        String expected = "BROBNAR,LOGOS,SANCTUM";
        House[] houses = {House.BROBNAR, House.LOGOS, House.SANCTUM};
        assertEquals(expected, HouseArrayTypeConverter.fromArray(houses));
    }

    @Test
    public void fromString() {
        String actual = "BROBNAR,LOGOS,SANCTUM";
        House[] houses = {House.BROBNAR, House.LOGOS, House.SANCTUM};
        assertArrayEquals(houses, HouseArrayTypeConverter.fromString(actual));
    }

    @Test
    public void fromHouse() {
        String expected = "BROBNAR";
        House house = House.BROBNAR;
        assertEquals(expected, HouseArrayTypeConverter.fromHouse(house));
    }

    @Test
    public void fromSingleString() {
        House expected = House.BROBNAR;
        String actual = "BROBNAR";
        assertEquals(expected, HouseArrayTypeConverter.fromSingleString(actual));
    }
}
