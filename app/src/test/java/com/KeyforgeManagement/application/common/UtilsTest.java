package com.KeyforgeManagement.application.common;

import com.KeyforgeManagement.application.data.model.House;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void enumValueToString() {
        assertEquals("Logos", Utils.enumValueToString(House.LOGOS.name()));
        assertEquals("Star Alliance", Utils.enumValueToString(House.STAR_ALLIANCE.name()));
    }
}