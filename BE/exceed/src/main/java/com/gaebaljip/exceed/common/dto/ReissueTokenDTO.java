package com.gaebaljip.exceed.common.dto;

import lombok.Builder;

public record ReissueTokenDTO(String accessToken, String refreshToken) {
    @Builder
    public ReissueTokenDTO {
    }
}
