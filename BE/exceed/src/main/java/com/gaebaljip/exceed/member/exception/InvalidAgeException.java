package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

@Getter
public class InvalidAgeException extends IllegalArgumentException {

    private final MessageCode messageCode;

    public InvalidAgeException() {
        super(MessageCode.INVALID_AGE.getValue());
        this.messageCode = MessageCode.INVALID_AGE;
    }
}
