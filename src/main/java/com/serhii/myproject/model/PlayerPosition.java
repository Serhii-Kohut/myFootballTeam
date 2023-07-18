package com.serhii.myproject.model;

public enum PlayerPosition {
    GOALKEEPER("Goalkeeper"),
    DEFENDER("Defender"),
    MIDFIELDER("Midfielder"),
    FORWARD("Forward");

    private final String value;

    PlayerPosition(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

