package com.gaebaljip.exceed.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gaebaljip.exceed.common.Error;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final boolean success = false;
    private final int status;
    private final String code;
    private final String reason;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss",
            timezone = "Asia/Seoul")
    private final LocalDateTime timeStamp;

    private final String path;

    public ErrorResponse(Error errorReason, String path) {
        this.status = Integer.parseInt(errorReason.getStatus());
        this.code = errorReason.getCode();
        this.reason = errorReason.getReason();
        this.timeStamp = LocalDateTime.now();
        this.path = path;
    }

    public ErrorResponse(int status, String code, String reason, String path) {
        this.status = status;
        this.code = code;
        this.reason = reason;
        this.timeStamp = LocalDateTime.now();
        this.path = path;
    }
}

