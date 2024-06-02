package com.gaebaljip.exceed.dto.response;

import lombok.Builder;

public record EatMealResponse(String presignedUrl) {

    @Builder
    public EatMealResponse {}
}
