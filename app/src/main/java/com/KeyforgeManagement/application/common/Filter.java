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
        List<DeckDTO> newList = new ArrayList<>(decks);
        List<DeckDTO> toRemove = new ArrayList<>();
        List<String> parameterList = Arrays.asList(filter.substring(1)
                .toUpperCase()
                .replace(" ", "_")
                .split(","));
        for (int i = 0; i < parameterList.size(); i++) {
            int finalI = i;
            newList.forEach(item -> {
                if (HouseArrayTypeConverter.fromSingleString(parameterList.get(finalI)) != null) {
                    boolean isIn = false;
                    for (int j = 0; j < 3; j++) {
                        if (HouseArrayTypeConverter
                                .fromSingleString(parameterList.get(finalI))
                                .equals(item.getDeck().getHouses()[j]))
                            isIn = true;
                    }
                    if (!isIn)
                        toRemove.add(item);
                } else if (ExpansionTypeConverter.fromString(parameterList.get(finalI)) != null) {
                    if (!ExpansionTypeConverter
                            .fromString(parameterList.get(finalI))
                            .equals(item.getDeck().getExpansion()))
                        toRemove.add(item);
                } else toRemove.add(item);
            });
            newList.removeAll(toRemove);
        }
        return newList;
    }
}
