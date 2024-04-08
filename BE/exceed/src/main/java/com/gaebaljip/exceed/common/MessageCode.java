package com.gaebaljip.exceed.common;

public enum MessageCode {
    INVALID_JWT("5000", "잘못된 토큰입니다."),
    EXPIRED_JWT("5001", "만료된 토큰입니다."),
    UNSUPPORTED_JWT("5002", "지원되지 않는 토큰입니다.");

    private final String code;
    private final String value;

    MessageCode(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}
