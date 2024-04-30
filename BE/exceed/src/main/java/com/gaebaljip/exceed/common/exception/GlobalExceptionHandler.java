package com.gaebaljip.exceed.common.exception;

import java.net.BindException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.gaebaljip.exceed.common.ApiResponse;
import com.gaebaljip.exceed.common.ApiResponseGenerator;
import com.gaebaljip.exceed.common.Error;
import com.gaebaljip.exceed.security.exception.ExpiredJwtException;
import com.gaebaljip.exceed.security.exception.InvalidJwtException;
import com.gaebaljip.exceed.security.exception.SecurityErrorCode;
import com.gaebaljip.exceed.security.exception.UnSupportedJwtException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ExpiredJwtException.class)
    protected ApiResponse<?> handleExpiredJwtAuthenticationException(ExpiredJwtException e) {
        return ApiResponseGenerator.fail(
                SecurityErrorCode.EXPIRED_JWT.getCode(), e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnSupportedJwtException.class)
    protected ApiResponse<?> handleUnsupportedJwtException(UnSupportedJwtException e) {
        return ApiResponseGenerator.fail(
                SecurityErrorCode.UNSUPPORTED_JWT.getCode(),
                e.getMessage(),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidJwtException.class)
    protected ApiResponse<?> handleInvalidJwtException(InvalidJwtException e) {
        return ApiResponseGenerator.fail(
                SecurityErrorCode.INVALID_JWT.getCode(), e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ApiResponse<?> handleNoHandlerFoundException(HttpMessageNotReadableException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /** 존재하지 않는 경로로 요청할 때 발생 */
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ApiResponse<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidFormatException.class)
    protected ApiResponse<?> handleInvalidFormatExceptionException(InvalidFormatException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /** javax.validation.Valid 또는 @Validated binding error가 발생할 경우 */
    @ExceptionHandler(BindException.class)
    protected ApiResponse<?> handleBindException(BindException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /** 주로 @RequestParam enum으로 binding 못했을 경우 발생 */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ApiResponse<?> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /** 지원하지 않은 HTTP method 호출 할 경우 발생 */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ApiResponse<?> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ApiResponse<?> handleExpiredJwtAuthenticationException(AuthenticationException e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    /** 도메인 에러 */
    @ExceptionHandler(EatCeedException.class)
    protected ApiResponse<?> handleEatCeedException(
            EatCeedException e, HttpServletRequest request) {
        BaseError code = e.getErrorCode();
        Error error = code.getError();
        return ApiResponseGenerator.fail(
                error.getCode(), error.getReason(), HttpStatus.valueOf(error.getStatus()));
    }

    /** 나머지 예외 발생 */
    @ExceptionHandler(Exception.class)
    protected ApiResponse<?> handleException(Exception e) {
        return ApiResponseGenerator.fail(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ApiResponse<?> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        return ApiResponseGenerator.fail(
                e.getBindingResult().getFieldErrors().get(0).getDefaultMessage(),
                HttpStatus.BAD_REQUEST);
    }
}
