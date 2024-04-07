package com.gaebaljip.exceed.common.exception;

import com.gaebaljip.exceed.common.Error;

public interface BaseErrorCode {
    public Error getErrorReason();

    String getExplainError() throws NoSuchFieldException;
}
