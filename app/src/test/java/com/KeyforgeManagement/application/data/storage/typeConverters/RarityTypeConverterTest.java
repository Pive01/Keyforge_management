package com.KeyforgeManagement.application.data.storage.typeConverters;

import com.KeyforgeManagement.application.data.model.Rarity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RarityTypeConverterTest {

    @Test
    public void fromRarity() {
        String expected = "COMMON";
        Rarity rarity = Rarity.COMMON;
        assertEquals(expected, RarityTypeConverter.fromRarity(rarity));
    }

    @Test
    public void fromString() {
        String rarity = "COMMON";
        Rarity expected = Rarity.COMMON;
        assertEquals(expected, RarityTypeConverter.fromString(rarity));
    }
}