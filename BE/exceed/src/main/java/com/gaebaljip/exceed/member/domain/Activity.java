package com.gaebaljip.exceed.member.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Activity {

    NOT_ACTIVE(1.2),
    LIGHTLY_ACTIVE(1.3),
    NORMAL_ACTIVE(1.5),
    VERY_ACTIVE(1.7),
    EXTREMELY_ACTIVE(1.9);

    private final double value;

    Activity(final double value) {
        this.value = value;
    }

    @JsonCreator
    public static Activity from(String value) {
        return Activity.valueOf(value.toUpperCase());
    }

}
