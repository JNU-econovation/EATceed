package com.gaebaljip.exceed.common;


public enum MessageCode {

    INTERNAL_SERVER_ERROR("500", "서버 내부 에러, BE를 불러주세요"),
    INVALID_HEIGHT("4444", "키는 음수일 수 없습니다."),
    INVALID_WEIGHT("4445", "몸무게가 음수일 수 없습니다."),
    INVALID_AGE("4446", "나이는 음수일 수 없습니다."),

    INVALID_GENDER("4447", "성별은 1과 0으로만 표현됩니다."),
    ;


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


