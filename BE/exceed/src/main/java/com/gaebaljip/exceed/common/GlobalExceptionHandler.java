package com.gaebaljip.exceed.common;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gaebaljip.exceed.security.exception.ExpiredJwtAuthenticationException;
import com.gaebaljip.exceed.security.exception.InvalidJwtAuthenticationException;
import com.gaebaljip.exceed.security.exception.UnsupportedAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ApiResponse<?> handleNoHandlerFoundException(HttpMessageNotReadableException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 존재하지 않는 경로로 요청할 때 발생
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ApiResponse<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    protected ApiResponse<?> handleInvalidFormatExceptionException(InvalidFormatException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * javax.validation.Valid 또는 @Validated binding error가 발생할 경우
     */
    @ExceptionHandler(BindException.class)
    protected ApiResponse<?> handleBindException(BindException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ApiResponse<?> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ApiResponse<?> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(ExpiredJwtAuthenticationException.class)
    protected ApiResponse<?> handleExpiredJwtAuthenticationException(ExpiredJwtAuthenticationException e) {
        return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    protected ApiResponse<?> handleExpiredJwtAuthenticationException(InvalidJwtAuthenticationException e) {
        return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnsupportedAuthenticationException.class)
    protected ApiResponse<?> handleExpiredJwtAuthenticationException(UnsupportedAuthenticationException e) {
        return ApiResponseGenerator.fail(e.getMessageCode().getCode(), e.getMessageCode().getValue(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ApiResponse<?> handleExpiredJwtAuthenticationException(AuthenticationException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * 나머지 예외 발생
     */
    @ExceptionHandler(Exception.class)
    protected ApiResponse<?> handleException(Exception e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ApiResponse<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return ApiResponseGenerator.fail(
                e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }
}
