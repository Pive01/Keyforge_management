package com.Keyforge_management.common;

import com.Keyforge_management.data.model.House;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void enumValueToString() {
        assertEquals("Logos", Utils.enumValueToString(House.LOGOS.name()));
        assertEquals("Star Alliance", Utils.enumValueToString(House.STAR_ALLIANCE.name()));
    }
}