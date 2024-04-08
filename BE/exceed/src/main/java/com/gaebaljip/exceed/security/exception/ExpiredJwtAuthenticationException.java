package com.gaebaljip.exceed.security.exception;

import org.springframework.security.core.AuthenticationException;

import com.gaebaljip.exceed.common.MessageCode;

import lombok.Getter;

@Getter
public class ExpiredJwtAuthenticationException extends AuthenticationException {

    private final MessageCode messageCode;

    public ExpiredJwtAuthenticationException() {
        super(MessageCode.EXPIRED_JWT.getValue());
        this.messageCode = MessageCode.EXPIRED_JWT;
    }
}
