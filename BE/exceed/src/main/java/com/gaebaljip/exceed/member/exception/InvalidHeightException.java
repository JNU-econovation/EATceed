package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;

public class InvalidHeightException extends IllegalArgumentException {

    private final MessageCode messageCode;

    public InvalidHeightException(String message) {
        super(message);
        this.messageCode = MessageCode.INVALID_HEIGHT;
    }
}
