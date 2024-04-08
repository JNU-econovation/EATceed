package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

public record LoginResponse(String accessToken, String refreshToken) {
    @Builder
    public LoginResponse {}
}
