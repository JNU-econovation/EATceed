package com.gaebaljip.exceed.member.domain;

public enum Gender {
    MALE(1),
    FEMALE(2);

    private final int value;

    Gender(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Gender of(final int value) {
        return switch (value) {
            case 1 -> MALE;
            case 2 -> FEMALE;
            default -> throw new IllegalArgumentException();
        };
    }
}
