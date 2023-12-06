package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;

public class InvalidAgeException extends IllegalArgumentException {

    private final MessageCode messageCode;

    public InvalidAgeException(String message) {
        super(message);
        this.messageCode = MessageCode.INVALID_AGE;
    }
}
