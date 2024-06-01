package com.gaebaljip.exceed.security.exception;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

@Getter
public class InvalidJwtException extends AuthenticationException {

    public static AuthenticationException EXECPTION = new InvalidJwtException();

    private InvalidJwtException() {
        super(SecurityErrorCode.INVALID_JWT.getReason());
    }
}
