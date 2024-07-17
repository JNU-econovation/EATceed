package com.gaebaljip.exceed.common.dto.response;

import java.util.List;

public record GetFoodsAutoResponse(List<String> foodJson) {
    public static GetFoodsAutoResponse from(List<String> foodJson) {
        return new GetFoodsAutoResponse(foodJson);
    }
}
