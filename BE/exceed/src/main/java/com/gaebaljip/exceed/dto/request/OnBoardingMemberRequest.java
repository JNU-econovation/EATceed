package com.gaebaljip.exceed.dto.request;

import com.gaebaljip.exceed.common.annotation.Enum;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.domain.Gender;

import lombok.Builder;

public record OnBoardingMemberRequest(
        Double height,
        @Enum(enumClass = Gender.class) String gender,
        Double weight,
        Double targetWeight,
        Integer age,
        @Enum(enumClass = Activity.class) String activity,
        String etc) {

    @Builder
    public OnBoardingMemberRequest {}
}
