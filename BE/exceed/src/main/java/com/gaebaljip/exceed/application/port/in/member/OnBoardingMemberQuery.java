package com.gaebaljip.exceed.application.port.in.member;

import lombok.Builder;

@Builder
public record OnBoardingMemberQuery(Long memberId) {
    public static OnBoardingMemberQuery of(Long memberId) {
        return OnBoardingMemberQuery.builder().memberId(memberId).build();
    }
}
