package com.gaebaljip.exceed.nutritionist.exception;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.gaebaljip.exceed.achieve")
public class AchieveExceptionHandler {
    @ExceptionHandler(MealNotFoundException.class)
    public ApiResponse<?> handleInvalidGenderException(MealNotFoundException e) {
        return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
