package com.gaebaljip.exceed.dto;

import com.gaebaljip.exceed.common.annotation.Enum;
import com.gaebaljip.exceed.member.domain.Activity;
import lombok.Builder;

public record CreateMemberRequest(
        Double height,
        Boolean gender,
        Double weight,
        Integer age,
        @Enum(enumClass = Activity.class)
        String activity,
        String etc
) {

        @Builder
        public CreateMemberRequest {
        }
}
