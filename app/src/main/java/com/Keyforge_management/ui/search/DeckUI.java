package com.Keyforge_management.ui.search;

import com.Keyforge_management.data.model.House;

public class DeckUI {
    private String name;
    private String set;
    private int sas;
    private int amber;
    private House[] houses;

    public DeckUI(String name, String set, int sas, int amber, House[] houses) {
        this.name = name;
        this.set = set;
        this.sas = sas;
        this.amber = amber;
        this.houses = houses;
    }

    public House[] getHouses() {
        return houses;
    }

    public int getAmber() {
        return amber;
    }

    public int getSas() {
        return sas;
    }

    public String getSet() {
        return set;
    }

    public String getName() {
        return name;
    }
}
