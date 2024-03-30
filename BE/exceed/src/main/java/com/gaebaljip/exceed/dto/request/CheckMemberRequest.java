package com.gaebaljip.exceed.dto.request;

import lombok.Builder;

import javax.validation.constraints.NotNull;

public record CheckMemberRequest(
        @NotNull String email,
        @NotNull String code
) {
    @Builder
    public CheckMemberRequest {
    }
}
