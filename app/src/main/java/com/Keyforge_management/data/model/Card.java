package com.Keyforge_management.data.model;

import com.Keyforge_management.data.storage.typeConverters.HouseArrayTypeConverter;
import com.Keyforge_management.data.storage.typeConverters.RarityTypeConverter;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "cards")
public class Card implements Serializable {
    @PrimaryKey
    @NonNull
    private String id;
    private String card_title;
    private String card_type;
    @TypeConverters({HouseArrayTypeConverter.class})
    private House house;
    @TypeConverters({RarityTypeConverter.class})
    private Rarity rarity;
    private String card_text;
    private int amber;
    private String front_image;
    private Boolean is_maverick;
    private Boolean is_legacy;
    private Boolean is_anomaly;

    public Boolean getIs_legacy() {
        return is_legacy;
    }

    public void setIs_legacy(Boolean is_legacy) {
        this.is_legacy = is_legacy;
    }

    public Boolean getIs_maverick() {
        return is_maverick;
    }

    public void setIs_maverick(Boolean is_maverick) {
        this.is_maverick = is_maverick;
    }

    public Boolean getIs_anomaly() {
        return is_anomaly;
    }

    public void setIs_anomaly(Boolean is_anomaly) {
        this.is_anomaly = is_anomaly;
    }


    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }



    public Card(String card_title) {
        this.card_title = card_title;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFront_image() {
        return front_image;
    }

    public void setFront_image(String front_image) {
        this.front_image = front_image;
    }

    public String getCard_title() {
        return card_title;
    }

    public void setCard_title(String card_title) {
        this.card_title = card_title;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public String getCard_text() {
        return card_text;
    }

    public void setCard_text(String card_text) {
        this.card_text = card_text;
    }

    public int getAmber() {
        return amber;
    }

    public void setAmber(int amber) {
        this.amber = amber;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", card_title='" + card_title + '\'' +
                ", card_type='" + card_type + '\'' +
                ", house=" + house +
                ", card_text='" + card_text + '\'' +
                ", amber=" + amber +
                ", front_image='" + front_image + '\'' +
                ", is_maverick=" + is_maverick +
                ", is_legacy=" + is_legacy +
                ", rarity=" + rarity +
                ", is_anomaly=" + is_anomaly +
                '}';
    }
}
