package com.gaebaljip.exceed.adapter.in.meal.response;

import lombok.Builder;

public record EatMealResponse(String presignedUrl) {

    @Builder
    public EatMealResponse {}
}
