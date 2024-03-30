package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

@Getter
public class InvalidCodeException extends IllegalArgumentException{

    private final MessageCode messageCode;

    public InvalidCodeException() {
        super(MessageCode.INVALID_CODE.getValue());
        this.messageCode = MessageCode.INVALID_CODE;
    }
}
