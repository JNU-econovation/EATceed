package com.gaebaljip.exceed.security.exception;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

@Getter
public class UnsupportedJwtException extends AuthenticationException {

    public static AuthenticationException EXECPTION = new UnsupportedJwtException();

    private UnsupportedJwtException() {
        super(SecurityErrorCode.UNSUPPORTED_JWT.getCode());
    }
}
