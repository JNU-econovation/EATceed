package com.gaebaljip.exceed.member.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

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
        if (value == null)
            return null;
        return Arrays.stream(values())
                .filter(var -> var.name().equals(value))
                .findAny()
                .orElse(null);
    }

    @JsonValue
    public double getValue() {
        return value;
    }


}
