package com.gaebaljip.exceed.dto.request;

import com.gaebaljip.exceed.common.ValidationMessage;
import lombok.Builder;

import javax.validation.constraints.Email;

public record LoginRequest(
    @Email(message = ValidationMessage.INVALID_EMAIL) String email,
    String password
) {
    @Builder
    public LoginRequest {
    }
}
