package com.gaebaljip.exceed.dto.request;

import javax.validation.constraints.NotNull;

import lombok.Builder;

public record CheckMemberRequest(@NotNull String email, @NotNull String code) {
    @Builder
    public CheckMemberRequest {}
}
