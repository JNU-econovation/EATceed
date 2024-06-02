package com.gaebaljip.exceed.auth.exception;

import com.gaebaljip.exceed.common.exception.EatCeedException;

import lombok.Getter;

@Getter
public class PasswordMismatchException extends EatCeedException {

    public static EatCeedException EXECPTION = new PasswordMismatchException();

    public PasswordMismatchException() {
        super(AuthError.PASSWORD_MISMATCH);
    }
}
