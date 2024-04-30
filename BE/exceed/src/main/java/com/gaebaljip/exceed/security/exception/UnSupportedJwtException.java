package com.gaebaljip.exceed.security.exception;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

@Getter
public class UnSupportedJwtException extends AuthenticationException {

    public static AuthenticationException EXECPTION = new UnSupportedJwtException();

    private UnSupportedJwtException() {
        super(SecurityErrorCode.UNSUPPORTED_JWT.getCode());
    }
}
