package com.gaebaljip.exceed.common.dto;

import lombok.Builder;

public record TokenDTO(String accessToken, String refreshToken) {
    @Builder
    public TokenDTO {}
}
