package com.gaebaljip.exceed.common.exception;

import com.gaebaljip.exceed.common.MessageCode;
import lombok.Getter;

@Getter
public class DecryptionErrorException extends RuntimeException{

    private final MessageCode messageCode;

    public DecryptionErrorException() {
        super(MessageCode.DECRYPTION_FAIL.getValue());
        this.messageCode = MessageCode.DECRYPTION_FAIL;
    }
}
