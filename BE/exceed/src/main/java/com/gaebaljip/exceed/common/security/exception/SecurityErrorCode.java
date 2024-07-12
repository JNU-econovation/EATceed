package com.gaebaljip.exceed.common.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode {
    INVALID_JWT(401, "5000", "잘못된 토큰입니다."),
    EXPIRED_JWT(401, "5001", "만료된 토큰입니다."),
    UNSUPPORTED_JWT(401, "5002", "지원되지 않는 토큰입니다."),
    SIGNATURE_JWT(401, "5003", "토큰의 형식이 잘못 됬습니다."),
    NEED_AUTHENTICATION(401, "5004", "인증이 필요합니다.");

    private final Integer status;
    private final String code;
    private final String reason;
}
