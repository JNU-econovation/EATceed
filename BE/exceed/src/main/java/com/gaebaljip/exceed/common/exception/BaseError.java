package com.gaebaljip.exceed.common.exception;

import com.gaebaljip.exceed.common.Error;

public interface BaseError {
    public Error getError();

    String getExplainError() throws NoSuchFieldException;
}
