package com.gaebaljip.exceed.common;


public enum MessageCode {

    INTERNAL_SERVER_ERROR("500", "서버 내부 에러");

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


