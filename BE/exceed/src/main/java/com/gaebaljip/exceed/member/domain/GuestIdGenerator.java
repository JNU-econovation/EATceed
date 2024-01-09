package com.gaebaljip.exceed.member.domain;

import lombok.Getter;

@Getter
public class GuestIdGenerator {

    private final String suffix = "+GUEST";

    private String guestId;

    public GuestIdGenerator() {
        this.guestId = generate();
    }

    private String generate() {
        return new RandomWord().getWord() + new RandomIndexes().getIndexes().toString() + suffix;
    }

}
