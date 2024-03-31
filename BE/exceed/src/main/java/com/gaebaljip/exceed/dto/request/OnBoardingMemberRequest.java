package com.gaebaljip.exceed.dto.request;

import com.gaebaljip.exceed.common.annotation.Enum;
import com.gaebaljip.exceed.member.domain.Activity;
import lombok.Builder;

public record
OnBoardingMemberRequest(
        Double height,
        Integer gender,
        Double weight,

        Double targetWeight,
        Integer age,
        @Enum(enumClass = Activity.class)
        String activity,
        String etc
) {

        @Builder
        public OnBoardingMemberRequest {
        }
}
