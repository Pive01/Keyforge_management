package com.KeyforgeManagement.application.ui.charts;

public class DeckScore {
    private double score;
    private String id;

    public DeckScore(double score, String id) {
        this.score = score;
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
