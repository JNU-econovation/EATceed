package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

public record CreateGuest(String loginId, String password, Long memberId) {

    @Builder
    public CreateGuest {
    }
}
