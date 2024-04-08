package com.gaebaljip.exceed.security.exception;

import org.springframework.security.core.AuthenticationException;

import com.gaebaljip.exceed.common.MessageCode;

import lombok.Getter;

@Getter
public class UnsupportedAuthenticationException extends AuthenticationException {

    private final MessageCode messageCode;

    public UnsupportedAuthenticationException() {
        super(MessageCode.UNSUPPORTED_JWT.getValue());
        this.messageCode = MessageCode.UNSUPPORTED_JWT;
    }
}
