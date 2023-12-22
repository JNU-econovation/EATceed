package com.gaebaljip.exceed.food.exception;

import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.gaebaljip.exceed.food")
public class FoodExceptionHandler {

    @ExceptionHandler(FoodNotFoundException.class)
    public ApiResponse<?> handleFoodNotFoundException(FoodNotFoundException e) {
        return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(), HttpStatus.BAD_REQUEST);
    }
}
