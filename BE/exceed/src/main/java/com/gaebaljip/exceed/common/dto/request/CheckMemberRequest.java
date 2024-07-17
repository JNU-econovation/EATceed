package com.gaebaljip.exceed.common.dto.request;

import javax.validation.constraints.NotNull;

import com.gaebaljip.exceed.common.ValidationMessage;

import lombok.Builder;

public record CheckMemberRequest(
        @NotNull(message = "이메일을 " + ValidationMessage.NOT_NULL) String email,
        @NotNull(message = "코드를 " + ValidationMessage.NOT_NULL) String code) {
    @Builder
    public CheckMemberRequest {}
}
