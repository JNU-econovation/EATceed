package com.gaebaljip.exceed.dto.request;

import javax.validation.constraints.Email;

import com.gaebaljip.exceed.common.ValidationMessage;
import com.gaebaljip.exceed.common.annotation.Password;

import lombok.Builder;

public record SignUpMemberRequest(
        @Email(message = ValidationMessage.INVALID_EMAIL) String email,
        @Password(message = ValidationMessage.INVALID_PASSWORD) String password) {
    @Builder
    public SignUpMemberRequest {}
}
