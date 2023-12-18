package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;

public class InvalidGenderException extends IllegalArgumentException {
    private final MessageCode messageCode;

    public InvalidGenderException() {
        super(MessageCode.INVALID_GENDER.getValue());
        this.messageCode = MessageCode.INVALID_GENDER;
    }

}
