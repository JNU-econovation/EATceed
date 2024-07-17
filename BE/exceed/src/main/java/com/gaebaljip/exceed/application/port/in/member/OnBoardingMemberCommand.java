package com.gaebaljip.exceed.application.port.in.member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.gaebaljip.exceed.application.domain.member.Activity;
import com.gaebaljip.exceed.application.domain.member.Gender;
import com.gaebaljip.exceed.adapter.in.member.request.OnBoardingMemberRequest;

import lombok.Builder;

public record OnBoardingMemberCommand(
        @NotBlank(message = "memberId must not be null") Long memberId,
        @NotBlank(message = "height must not be null") Double height,
        @NotBlank(message = "gender must not be null") Gender gender,
        @NotBlank(message = "weight must not be null") Double weight,
        @NotBlank(message = "target weight must not be null") Double targetWeight,
        @NotBlank(message = "age must not be null") Integer age,
        @NotNull(message = "activity must not be null") Activity activity,
        String etc) {

    @Builder
    public OnBoardingMemberCommand {}

    public static OnBoardingMemberCommand of(Long memberId, OnBoardingMemberRequest request) {
        return OnBoardingMemberCommand.builder()
                .memberId(memberId)
                .height(request.height())
                .gender(Gender.valueOf(request.gender()))
                .weight(request.weight())
                .targetWeight(request.targetWeight())
                .age(request.age())
                .activity(Activity.valueOf(request.activity()))
                .etc(request.etc())
                .build();
    }
}
