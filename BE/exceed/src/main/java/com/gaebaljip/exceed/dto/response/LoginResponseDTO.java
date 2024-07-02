package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

public record LoginResponseDTO(String accessToken, String refreshToken) {
    @Builder
    public LoginResponseDTO {}
}
