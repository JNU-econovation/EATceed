package com.gaebaljip.exceed.dto.request;

import javax.validation.constraints.NotBlank;

import com.gaebaljip.exceed.common.annotation.Enum;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.domain.Gender;

public record UpdateMemberRequest(
        @NotBlank(message = "memberId must not be null") Long memberId,
        @NotBlank(message = "memberId must not be null") Double height,
        @Enum(enumClass = Gender.class) String gender,
        @NotBlank(message = "memberId must not be null") Double weight,
        @NotBlank(message = "memberId must not be null") Double targetWeight,
        @NotBlank(message = "memberId must not be null") Integer age,
        @Enum(enumClass = Activity.class) String activity,
        @NotBlank(message = "memberId must not be null") String etc) {}
