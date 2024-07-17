package com.gaebaljip.exceed.common.dto;

import lombok.Builder;

public record LoginResponseDTO(String accessToken, String refreshToken) {
    @Builder
    public LoginResponseDTO {}
}
