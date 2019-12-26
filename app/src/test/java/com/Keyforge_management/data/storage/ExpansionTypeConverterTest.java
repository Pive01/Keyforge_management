package com.Keyforge_management.data.storage;

import com.Keyforge_management.data.model.Expansion;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpansionTypeConverterTest {

    @Test
    public void fromExpansion() {
        assertEquals("Call Of The Archons", ExpansionTypeConverter.fromExpansion(Expansion.CALL_OF_THE_ARCHONS));
    }

    @Test
    public void fromString() {
        assertEquals(Expansion.CALL_OF_THE_ARCHONS, ExpansionTypeConverter.fromString("Call Of The Archons"));
    }
}