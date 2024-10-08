package com.gaebaljip.exceed.common.exception.auth;

import com.gaebaljip.exceed.common.exception.EatCeedException;

public class NotFoundRefreshTokenException extends EatCeedException {

    public static EatCeedException EXECPTION = new NotFoundRefreshTokenException();

    public NotFoundRefreshTokenException() {
        super(AuthError.NOT_FOUND_REFRESHTOKEN);
    }
}
