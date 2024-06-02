package com.gaebaljip.exceed.common.exception;

import lombok.Getter;

@Getter
public class DecryptionErrorException extends EatCeedException {

    public static EatCeedException EXECPTION = new DecryptionErrorException();

    public DecryptionErrorException() {
        super(EncryptionError.DECRYPTION_FAIL);
    }
}
