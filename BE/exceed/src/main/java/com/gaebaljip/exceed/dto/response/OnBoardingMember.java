package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

public record OnBoardingMember(String loginId, String password, Long memberId) {

    @Builder
    public OnBoardingMember {}
}
