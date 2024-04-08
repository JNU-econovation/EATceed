package com.gaebaljip.exceed.security.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class UnsupportedAuthenticationException extends EatCeedException {

    public static EatCeedException EXECPTION = new UnsupportedAuthenticationException();

    private UnsupportedAuthenticationException() {
        super(SecurityErrorCode.UNSUPPORTED_JWT);
    }
}
