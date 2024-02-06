package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

public record GetFood(
        Long id,
        String name) {

    @Builder
    public GetFood {
    }
}
