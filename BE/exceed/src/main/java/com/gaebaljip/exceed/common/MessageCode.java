package com.gaebaljip.exceed.common;


public enum MessageCode {

    INTERNAL_SERVER_ERROR("500", "서버 내부 에러, BE를 불러주세요"),
    INVALID_HEIGHT("4444", "키는 음수일 수 없습니다."),
    INVALID_WEIGHT("4445", "몸무게가 음수일 수 없습니다."),
    INVALID_AGE("4446", "나이는 음수일 수 없습니다."),

    INVALID_GENDER("4447", "성별은 1과 0으로만 표현됩니다."),
    INVALID_MEMBER("4448", "존재하지 않는 회원입니다."),
    INVALID_MULTIPLE("4449", "0인분보다 커야하고, 100인분보다 작아야합니다."),
    INVALID_FOOD("4450", "존재하지 않는 음식입니다."),
    INVALID_MEAL("4451", "존재하지 않는 식사입니다."),
    EXTENTION_NOT_ALLOWED("4452", "이미지의 확장자는 jpg, jpeg, png만 가능합니다."),
    INVALID_JWT("5000", "잘못된 토큰입니다."),
    EXPIRED_JWT("5001", "만료된 토큰입니다."),
    UNSUPPORTED_JWT("5002", "지원되지 않는 토큰입니다."),
    INSUFFICIENT_MEALS("6000","하루 식사는 최소 한 끼 이상 제공되어야 합니다."),
    NOT_SAME_DATE("6001","하루 식사는 같은 날짜입니다.");


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


