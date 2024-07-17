package com.gaebaljip.exceed.adapter.in.member.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.gaebaljip.exceed.application.domain.member.Activity;
import com.gaebaljip.exceed.application.domain.member.Gender;
import com.gaebaljip.exceed.common.ValidationMessage;
import com.gaebaljip.exceed.common.annotation.Enum;

public record UpdateMemberRequest(
        @NotNull(message = "키를 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "키는 " + ValidationMessage.MIN_0)
                Double height,
        @Enum(enumClass = Gender.class) String gender,
        @NotNull(message = "나이를 " + ValidationMessage.NOT_NULL)
                @Min(value = 0, message = "나이는 " + ValidationMessage.MIN_0)
                Integer age,
        @Enum(enumClass = Activity.class) String activity,
        @NotBlank(message = "기타사항을 " + ValidationMessage.NOT_BLANK) String etc) {}
