package com.gaebaljip.exceed.common;


public enum MessageCode {

    INTERNAL_SERVER_ERROR("500", "서버 내부 에러, BE를 불러주세요"),


    INVALID_FOOD("4450", "존재하지 않는 음식입니다."),


    INVALID_JWT("5000", "잘못된 토큰입니다."),
    EXPIRED_JWT("5001", "만료된 토큰입니다."),
    UNSUPPORTED_JWT("5002", "지원되지 않는 토큰입니다."),



    ENCRYPTION_FAIL("8001","암호화에 실패하였습니다."),
    DECRYPTION_FAIL("8002", "복호화에 실패하였습니다.");



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


