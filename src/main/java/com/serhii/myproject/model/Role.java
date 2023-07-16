package com.serhii.myproject.model;

public enum Role {
    PRESIDENT("President"),
    SPORT_DIRECTOR("Sport Director"),
    COACH("Coach");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

