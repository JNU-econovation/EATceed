package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

public record CreateGuestResponse(String loginId, String password) {

    @Builder
    public CreateGuestResponse {
    }
}
