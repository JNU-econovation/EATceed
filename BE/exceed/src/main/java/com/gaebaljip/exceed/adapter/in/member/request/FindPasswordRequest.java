package com.gaebaljip.exceed.adapter.in.member.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.gaebaljip.exceed.common.ValidationMessage;
import com.gaebaljip.exceed.common.annotation.Password;

import lombok.Builder;

public record FindPasswordRequest(
        @Email(message = ValidationMessage.INVALID_EMAIL) String email,
        @Password(message = ValidationMessage.INVALID_PASSWORD) String newPassword,
        @NotBlank(message = "인증 코드를  " + ValidationMessage.NOT_BLANK) String code) {

    @Builder
    public FindPasswordRequest {}
}
