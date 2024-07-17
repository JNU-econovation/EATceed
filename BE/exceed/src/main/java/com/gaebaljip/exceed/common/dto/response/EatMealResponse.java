package com.gaebaljip.exceed.common.dto.response;

import lombok.Builder;

public record EatMealResponse(String presignedUrl) {

    @Builder
    public EatMealResponse {}
}
