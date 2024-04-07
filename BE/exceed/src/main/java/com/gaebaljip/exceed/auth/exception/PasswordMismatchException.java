package com.gaebaljip.exceed.auth.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

@Getter
public class PasswordMismatchException extends IllegalArgumentException{

    private final MessageCode messageCode;

    public PasswordMismatchException() {
        super(MessageCode.PASSWORD_MISMATCH.getValue());
        this.messageCode = MessageCode.PASSWORD_MISMATCH;
    }
}
