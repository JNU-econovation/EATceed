package com.gaebaljip.exceed.common.security.exception;

import org.springframework.security.core.AuthenticationException;

import lombok.Getter;

@Getter
public class SignatureJwtException extends AuthenticationException {

    public static AuthenticationException EXECPTION = new SignatureJwtException();

    public SignatureJwtException() {
        super(SecurityErrorCode.SIGNATURE_JWT.getReason());
    }
}
