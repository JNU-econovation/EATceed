package com.gaebaljip.exceed.dto.response;

import java.util.List;

public record GetFoodsResponse(List<String> foodNames) {
    public static GetFoodsResponse from(List<String> foodNames) {
        return new GetFoodsResponse(foodNames);
    }
}
