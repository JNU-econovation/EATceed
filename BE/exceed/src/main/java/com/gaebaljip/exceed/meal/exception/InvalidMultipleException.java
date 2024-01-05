package com.gaebaljip.exceed.meal.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

@Getter
public class InvalidMultipleException extends IllegalArgumentException {
    private final MessageCode messageCode;

    public InvalidMultipleException() {
        super(MessageCode.INVALID_MULTIPLE.getValue());
        this.messageCode = MessageCode.INVALID_MULTIPLE;
    }
}
