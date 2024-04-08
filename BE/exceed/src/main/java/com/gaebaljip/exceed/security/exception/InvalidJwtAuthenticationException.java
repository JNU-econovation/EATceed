package com.gaebaljip.exceed.security.exception;

import org.springframework.security.core.AuthenticationException;

import com.gaebaljip.exceed.common.MessageCode;

import lombok.Getter;

@Getter
public class InvalidJwtAuthenticationException extends AuthenticationException {

    private final MessageCode messageCode;

    public InvalidJwtAuthenticationException() {
        super(MessageCode.INVALID_JWT.getValue());
        this.messageCode = MessageCode.INVALID_JWT;
    }
}
