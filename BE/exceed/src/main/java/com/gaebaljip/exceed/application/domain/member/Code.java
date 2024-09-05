package com.gaebaljip.exceed.application.domain.member;

import java.util.UUID;

public class Code {
    public static String create() {
        return UUID.randomUUID().toString();
    }
}
