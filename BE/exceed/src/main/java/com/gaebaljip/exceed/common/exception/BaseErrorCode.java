package com.gaebaljip.exceed.common.exception;

import com.gaebaljip.exceed.common.Error;

public interface BaseErrorCode {
    public Error getError();

    String getExplainError() throws NoSuchFieldException;
}
