package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

public record GetFood(
        Long id,
        String name,
        String imageUri) {

    @Builder
    public GetFood {
    }
}
