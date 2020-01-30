package com.KeyforgeManagement.application.data.storage.typeConverters;

import com.KeyforgeManagement.application.data.model.Expansion;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpansionTypeConverterTest {

    @Test
    public void fromExpansion() {
        assertEquals("CALL_OF_THE_ARCHONS", ExpansionTypeConverter.fromExpansion(Expansion.CALL_OF_THE_ARCHONS));
    }

    @Test
    public void fromString() {
        assertEquals(Expansion.CALL_OF_THE_ARCHONS, ExpansionTypeConverter.fromString("CALL_OF_THE_ARCHONS"));
    }
}