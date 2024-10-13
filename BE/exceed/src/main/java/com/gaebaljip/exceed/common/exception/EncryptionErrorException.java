package com.gaebaljip.exceed.common.exception;

import lombok.Getter;

@Getter
public class EncryptionErrorException extends EatCeedException {

    public static EatCeedException EXECPTION = new EncryptionErrorException();

    public EncryptionErrorException() {
        super(GlobalError.ENCRYPTION_FAIL);
    }
}
