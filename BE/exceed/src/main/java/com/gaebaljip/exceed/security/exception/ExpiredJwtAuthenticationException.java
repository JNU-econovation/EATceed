package com.gaebaljip.exceed.security.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class ExpiredJwtAuthenticationException extends EatCeedException {

    public static EatCeedException EXECPTION = new ExpiredJwtAuthenticationException();

    private ExpiredJwtAuthenticationException() {
        super(SecurityErrorCode.EXPIRED_JWT);
    }
}
