package com.group7.mockexpert;

import java.util.List;

public class CueCardModel {
    private String title;
    private List<String> bulletPoints;

    // Constructor
    public CueCardModel(String title, List<String> bulletPoints) {
        this.title = title;
        this.bulletPoints = bulletPoints;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public List<String> getBulletPoints() {
        return bulletPoints;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBulletPoints(List<String> bulletPoints) {
        this.bulletPoints = bulletPoints;
    }
}
