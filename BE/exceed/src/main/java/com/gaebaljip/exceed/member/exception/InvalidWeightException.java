package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;

public class InvalidWeightException extends IllegalArgumentException {

    private final MessageCode messageCode;

    public InvalidWeightException(String message) {
        super(message);
        this.messageCode = MessageCode.INVALID_WEIGHT;
    }
}
