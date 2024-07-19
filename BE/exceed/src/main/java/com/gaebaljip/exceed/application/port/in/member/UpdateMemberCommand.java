package com.gaebaljip.exceed.application.port.in.member;

import com.gaebaljip.exceed.adapter.in.member.request.UpdateMemberRequest;

import lombok.Builder;

@Builder
public record UpdateMemberCommand(
        Long memberId, Double height, String gender, Integer age, String activity, String etc) {
    public static UpdateMemberCommand of(Long memberId, UpdateMemberRequest request) {
        return UpdateMemberCommand.builder()
                .memberId(memberId)
                .height(request.height())
                .gender(request.gender())
                .age(request.age())
                .activity(request.activity())
                .etc(request.etc())
                .build();
    }
}
