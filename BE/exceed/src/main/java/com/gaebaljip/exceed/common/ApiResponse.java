package com.gaebaljip.exceed.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Getter
public class ApiResponse<B> extends ResponseEntity<B> {

    public ApiResponse(B body, HttpStatus status) {
        super(body, status);
    }

    public ApiResponse(B body, MediaType mediaType, HttpStatus status) {
        super(body, status);
        this.getHeaders().setContentType(mediaType);
    }


    @Getter
    @AllArgsConstructor
    public static class CustomBody<D> implements Serializable {
        private Boolean success;
        private D response;
        private Error error;
    }
}
