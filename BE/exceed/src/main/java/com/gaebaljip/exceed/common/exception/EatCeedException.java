package com.gaebaljip.exceed.common.exception;

import com.gaebaljip.exceed.common.Error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EatCeedException extends RuntimeException {
    private BaseError errorCode;

    public Error getError() {
        return this.errorCode.getError();
    }
}
