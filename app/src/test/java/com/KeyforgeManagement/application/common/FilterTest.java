package com.KeyforgeManagement.application.common;

import com.KeyforgeManagement.application.data.model.Deck;
import com.KeyforgeManagement.application.data.model.DeckDTO;
import com.KeyforgeManagement.application.data.model.Expansion;
import com.KeyforgeManagement.application.data.model.House;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.KeyforgeManagement.application.data.model.Expansion.AGE_OF_ASCENSION;
import static com.KeyforgeManagement.application.data.model.Expansion.CALL_OF_THE_ARCHONS;
import static com.KeyforgeManagement.application.data.model.House.BROBNAR;
import static com.KeyforgeManagement.application.data.model.House.DIS;
import static com.KeyforgeManagement.application.data.model.House.LOGOS;
import static com.KeyforgeManagement.application.data.model.House.MARS;
import static com.KeyforgeManagement.application.data.model.House.SANCTUM;
import static com.KeyforgeManagement.application.data.model.House.SAURIAN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FilterTest {

    @Test
    public void filter_emptyList_returnsEmptyList() {
        String filter = "@mass_mutation";
        List<DeckDTO> decks = Collections.emptyList();

        List<DeckDTO> result = Filter.filter(filter, decks);

        assertTrue(result.isEmpty());
    }

    @Test
    public void filter_emptyHousesExpansionFilter_returnsEmptyList() {
        String filter = "@";
        List<DeckDTO> decks = listOf(
                createDeck(new House[]{MARS, BROBNAR, LOGOS}, CALL_OF_THE_ARCHONS),
                createDeck(new House[]{MARS, DIS, LOGOS}, AGE_OF_ASCENSION)
        );

        List<DeckDTO> result = Filter.filter(filter, decks);

        assertTrue(result.isEmpty());
    }

    @Test
    public void filter_emptyFilter_returnFullList() {
        String filter = "";
        List<DeckDTO> decks = listOf(
                createDeck(new House[]{MARS, BROBNAR, LOGOS}, CALL_OF_THE_ARCHONS, "Takeo"),
                createDeck(new House[]{MARS, DIS, LOGOS}, AGE_OF_ASCENSION, "Indigo")
        );

        List<DeckDTO> result = Filter.filter(filter, decks);

        assertEquals(2, result.size());
    }

    @Test
    public void filter_nameFilter_filtersByNames() {
        String filter = "eo";
        List<DeckDTO> decks = listOf(
                createDeck(new House[]{MARS, BROBNAR, LOGOS}, CALL_OF_THE_ARCHONS, "Takeo"),
                createDeck(new House[]{SANCTUM, DIS, LOGOS}, AGE_OF_ASCENSION, "Indigo")
        );

        List<DeckDTO> result = Filter.filter(filter, decks);
        assertEquals("Takeo", result.get(0).getDeck().getName());
    }

    @Test
    public void filter_housesFilter_filtersByHouses() {
        String filter = "@mars,logos";
        List<DeckDTO> decks = listOf(
                createDeck(new House[]{MARS, BROBNAR, LOGOS}, CALL_OF_THE_ARCHONS),
                createDeck(new House[]{SANCTUM, DIS, LOGOS}, AGE_OF_ASCENSION)
        );

        List<DeckDTO> result = Filter.filter(filter, decks);

        assertEquals(1, result.size());
        assertEquals(CALL_OF_THE_ARCHONS, result.get(0).getDeck().getExpansion());
    }

    @Test
    public void filter_expansionFilter_filtersByExpansion() {
        String filter = "@call_of_the_archons";
        List<DeckDTO> decks = listOf(
                createDeck(new House[]{MARS, BROBNAR, LOGOS}, CALL_OF_THE_ARCHONS),
                createDeck(new House[]{SANCTUM, DIS, LOGOS}, AGE_OF_ASCENSION)
        );

        List<DeckDTO> result = Filter.filter(filter, decks);

        assertEquals(1, result.size());
        assertEquals(CALL_OF_THE_ARCHONS, result.get(0).getDeck().getExpansion());
    }

    @Test
    public void filter_expansionHousesFilter_filtersByHousesExpansion() {
        String filter = "@call_of_the_archons,mars,logos";
        List<DeckDTO> decks = listOf(
                createDeck(new House[]{MARS, BROBNAR, LOGOS}, CALL_OF_THE_ARCHONS, "one"),
                createDeck(new House[]{MARS, BROBNAR, SAURIAN}, CALL_OF_THE_ARCHONS, "two"),
                createDeck(new House[]{SANCTUM, DIS, LOGOS}, AGE_OF_ASCENSION, "three")
        );

        List<DeckDTO> result = Filter.filter(filter, decks);

        assertEquals(1, result.size());
        assertEquals("one", result.get(0).getDeck().getName());
    }

    private static DeckDTO createDeck(House[] houses, Expansion expansion) {
        return createDeck(houses, expansion, "default name");
    }

    private static DeckDTO createDeck(House[] houses, Expansion expansion, String name) {
        Deck deck = new Deck();
        deck.setName(name);
        deck.setHouses(houses);
        deck.setExpansion(expansion);

        DeckDTO dto = new DeckDTO();
        dto.setDeck(deck);
        return dto;
    }

    private static <T> List<T> listOf(T... args) {
        return Arrays.asList(args);
    }
}
