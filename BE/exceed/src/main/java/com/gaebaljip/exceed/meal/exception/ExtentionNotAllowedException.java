package com.gaebaljip.exceed.meal.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

@Getter
public class ExtentionNotAllowedException extends IllegalArgumentException {

    private final MessageCode messageCode;

    public ExtentionNotAllowedException() {
        super(MessageCode.EXTENTION_NOT_ALLOWED.getValue());
        this.messageCode = MessageCode.EXTENTION_NOT_ALLOWED;
    }
}
