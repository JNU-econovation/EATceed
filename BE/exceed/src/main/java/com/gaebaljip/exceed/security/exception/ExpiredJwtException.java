package com.gaebaljip.exceed.security.exception;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

@Getter
public class ExpiredJwtException extends AuthenticationException {

    public static AuthenticationException EXECPTION = new ExpiredJwtException();

    private ExpiredJwtException() {
        super(SecurityErrorCode.EXPIRED_JWT.getReason());
    }
}
