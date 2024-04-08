package com.gaebaljip.exceed.common.exception;

import com.gaebaljip.exceed.common.MessageCode;

import lombok.Getter;

@Getter
public class EncryptionErrorException extends RuntimeException {

    private final MessageCode messageCode;

    public EncryptionErrorException() {
        super(MessageCode.ENCRYPTION_FAIL.getValue());
        this.messageCode = MessageCode.ENCRYPTION_FAIL;
    }
}
