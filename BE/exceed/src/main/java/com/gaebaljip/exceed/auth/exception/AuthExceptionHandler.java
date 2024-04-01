package com.gaebaljip.exceed.auth.exception;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.gaebaljip.exceed.auth")
public class AuthExceptionHandler {

    @ExceptionHandler(PasswordMismatchException.class)
    public ApiResponse<?> handlePasswordMismatchException(PasswordMismatchException e) {
        return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(), HttpStatus.BAD_REQUEST);
    }
}
