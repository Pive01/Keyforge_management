package com.KeyforgeManagement.application.data.storage.typeConverters;

import com.KeyforgeManagement.application.data.model.House;

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
        String actual = "SANCTUM,LOGOS,UNFATHOMABLE";
        House[] houses = {House.SANCTUM, House.LOGOS, House.UNFATHOMABLE};
        assertArrayEquals(houses, HouseArrayTypeConverter.fromString(actual));
    }

    @Test
    public void fromHouse() {
        String expected = "UNFATHOMABLE";
        House house = House.UNFATHOMABLE;
        assertEquals(expected, HouseArrayTypeConverter.fromHouse(house));
    }

    @Test
    public void fromSingleString() {
        House expected = House.BROBNAR;
        String actual = "BROBNAR";
        assertEquals(expected, HouseArrayTypeConverter.fromSingleString(actual));
    }
}
