package com.gaebaljip.exceed.common.dto.response;

public record GetOwnFoodResponse(Long foodId, String name) {
    public static GetOwnFoodResponse of(Long foodId, String name) {
        return new GetOwnFoodResponse(foodId, name);
    }
}
