package com.serhii.myproject.model;

import lombok.Getter;
@Getter
public enum Role {
    PRESIDENT("President", 1),
    SPORT_DIRECTOR("Sport Director", 2),
    COACH("Coach", 3);

    @Getter
    private final String value;
    private final int priority;

    Role(String value, int priority) {
        this.value = value;
        this.priority = priority;
    }

}

