package com.gaebaljip.exceed.member.application.port.in;

import com.gaebaljip.exceed.common.annotation.Enum;
import com.gaebaljip.exceed.dto.request.UpdateMemberRequest;
import com.gaebaljip.exceed.member.domain.Activity;
import com.gaebaljip.exceed.member.domain.Gender;
import lombok.Builder;

@Builder
public record UpdateMemberCommand(Long memberId,
                                  Double height,
                                  @Enum(enumClass = Gender.class) String gender,
                                  Double weight,
                                  Double targetWeight,
                                  Integer age,
                                  @Enum(enumClass = Activity.class) String activity,
                                  String etc) {
    public static UpdateMemberCommand of(Long memberId, UpdateMemberRequest request) {
        return UpdateMemberCommand.builder()
                .memberId(memberId)
                .height(request.height())
                .gender(request.gender())
                .weight(request.weight())
                .targetWeight(request.targetWeight())
                .age(request.age())
                .activity(request.activity())
                .etc(request.etc())
                .build();
    }
}