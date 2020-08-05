package com.KeyforgeManagement.application.common;

import com.KeyforgeManagement.application.data.model.DeckDTO;
import com.KeyforgeManagement.application.data.storage.typeConverters.ExpansionTypeConverter;
import com.KeyforgeManagement.application.data.storage.typeConverters.HouseArrayTypeConverter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Filter {

    private Filter() {
    }

    @NotNull
    public static List<DeckDTO> filter(String filter, List<DeckDTO> decks) {
        if (filter.isEmpty()) {
            return decks;
        }

        if (filter.startsWith("@")) {
            return filterByHousesExpansion(filter, decks);
        }

        return filterByName(filter, decks);
    }

    private static List<DeckDTO> filterByHousesExpansion(String filter, List<DeckDTO> decks) {
        List<DeckDTO> newList = new ArrayList<>(decks);
        List<DeckDTO> toRemove = new ArrayList<>();

        for (String parameter : getParameters(filter)) {
            newList.forEach(item -> {
                if (HouseArrayTypeConverter.fromSingleString(parameter) != null) {
                    boolean isIn = false;
                    for (int j = 0; j < 3; j++) {
                        if (HouseArrayTypeConverter
                                .fromSingleString(parameter)
                                .equals(item.getDeck().getHouses()[j]))
                            isIn = true;
                    }
                    if (!isIn)
                        toRemove.add(item);
                } else if (ExpansionTypeConverter.fromString(parameter) != null) {
                    if (!ExpansionTypeConverter
                            .fromString(parameter)
                            .equals(item.getDeck().getExpansion()))
                        toRemove.add(item);
                } else toRemove.add(item);
            });
            newList.removeAll(toRemove);
        }
        return newList;
    }

    private static List<DeckDTO> filterByName(String filter, List<DeckDTO> decks) {
        List<DeckDTO> newList = new ArrayList<>();
        decks.forEach(item -> {
            if (item.getDeck().getName().toLowerCase().contains(filter.toLowerCase()))
                newList.add(item);
        });
        return newList;
    }

    private static List<String> getParameters(String filter) {
        return Arrays.asList(filter.substring(1)
                .toUpperCase()
                .replace(" ", "_")
                .split(","));
    }
}
