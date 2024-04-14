package com.gaebaljip.exceed.security.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class InvalidJwtAuthenticationException extends EatCeedException {

    public static EatCeedException EXECPTION = new InvalidJwtAuthenticationException();

    private InvalidJwtAuthenticationException() {
        super(SecurityErrorCode.INVALID_JWT);
    }
}
