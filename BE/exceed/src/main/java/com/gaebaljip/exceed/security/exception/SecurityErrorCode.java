package com.gaebaljip.exceed.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SecurityErrorCode {
    INVALID_JWT(401, "5000", "잘못된 토큰입니다."),
    EXPIRED_JWT(401, "5001", "만료된 토큰입니다."),
    UNSUPPORTED_JWT(401, "5002", "지원되지 않는 토큰입니다.");

    private final Integer status;
    private final String code;
    private final String reason;
}
