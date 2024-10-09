package com.gaebaljip.exceed.common.dto;

import lombok.Builder;

public record HttpRequestDTO(String url, String method) {
    @Builder
    public HttpRequestDTO {}

    public static HttpRequestDTO of(String url, String method) {
        return new HttpRequestDTO(url, method);
    }
}
