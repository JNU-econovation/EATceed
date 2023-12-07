package com.gaebaljip.exceed.member.exception;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.gaebaljip.exceed.member")
@Slf4j
public class MemberExceptionHandler {

    @ExceptionHandler(InvalidHeightException.class)
    public ApiResponse<?> handleInvalidHeightException(InvalidHeightException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidWeightException.class)
    public ApiResponse<?> handleInvalidWeightException(InvalidWeightException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAgeException.class)
    public ApiResponse<?> handleInvalidAgeException(InvalidAgeException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
