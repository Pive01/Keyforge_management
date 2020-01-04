package com.Keyforge_management.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cards")
public class Card {
    @PrimaryKey
    @NonNull
    private String id;
    private String card_title;
    private String card_type;
    private String house;
    private String card_text;
    private int amber;
    private String front_image;

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

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
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
                ", house='" + house + '\'' +
                ", card_text='" + card_text + '\'' +
                ", amber=" + amber +
                ", front_image='" + front_image + '\'' +
                '}';
    }
}
