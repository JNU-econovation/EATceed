package com.gaebaljip.exceed.dto.request;

import com.gaebaljip.exceed.common.ValidationMessage;
import lombok.Builder;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

public record SignUpMemberRequest(

        @Email(message = ValidationMessage.INVALID_EMAIL)
        String email,
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\W).{6,}$", message = "비밀번호는 최소 6글자 이상이며, 영어와 특수문자를 포함해야 합니다.")
        String password
) {
    @Builder
    public SignUpMemberRequest {
    }
}
