package com.gaebaljip.exceed.member.application.port.in;

import com.gaebaljip.exceed.member.domain.Activity;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CreateMemberCommand(
        @NotBlank
        Double height,
        @NotNull
        Boolean gender,

        @NotBlank
        Double weight,

        @NotBlank
        Integer age,

        @NotNull(message = "activity must not be null")
        Activity activity,
        String etc) {

        @Builder
        public CreateMemberCommand {
        }

        ;


}
