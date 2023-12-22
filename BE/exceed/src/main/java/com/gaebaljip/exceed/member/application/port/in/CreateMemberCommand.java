package com.gaebaljip.exceed.member.application.port.in;

import com.gaebaljip.exceed.member.domain.Activity;
import lombok.Builder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CreateMemberCommand(
        @NotBlank(message = "height must not be null")
        Double height,
        @NotBlank(message = "gender must not be null")
        Integer gender,

        @NotBlank(message = "weight must not be null")
        Double weight,

        @NotBlank(message = "age must not be null")
        Integer age,

        @NotNull(message = "activity must not be null")
        Activity activity,
        String etc) {

        @Builder
        public CreateMemberCommand {
        }

        ;


}
