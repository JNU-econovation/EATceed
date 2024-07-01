package com.gaebaljip.exceed.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.gaebaljip.exceed.common.ValidationMessage;
import com.gaebaljip.exceed.common.annotation.Enum;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.domain.Gender;

import lombok.Builder;

public record OnBoardingMemberRequest(
        @NotNull(message = "키를 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "키는 " + ValidationMessage.MIN_0)
                Double height,
        @Enum(enumClass = Gender.class) String gender,
        @NotNull(message = "몸무게를 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "몸무게는 " + ValidationMessage.MIN_0)
                Double weight,
        @NotNull(message = "목표 몸무게를 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "목표 몸무게는 " + ValidationMessage.MIN_0)
                Double targetWeight,
        @NotNull(message = "나이를 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "나이는 " + ValidationMessage.MIN_0)
                Integer age,
        @Enum(enumClass = Activity.class) String activity,
        String etc) {

    @Builder
    public OnBoardingMemberRequest {}
}
