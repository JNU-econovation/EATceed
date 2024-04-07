package com.gaebaljip.exceed.common.exception;

import com.gaebaljip.exceed.common.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EatCeedException extends RuntimeException {
    private BaseErrorCode errorCode;

    public Error getErrorReason() {
        return this.errorCode.getErrorReason();
    }
}
