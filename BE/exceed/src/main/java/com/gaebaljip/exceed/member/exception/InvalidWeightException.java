package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

@Getter
public class InvalidWeightException extends IllegalArgumentException {

    private final MessageCode messageCode;

    public InvalidWeightException() {
        super(MessageCode.INVALID_WEIGHT.getValue());
        this.messageCode = MessageCode.INVALID_WEIGHT;
    }
}
