package com.gaebaljip.exceed.adapter.in.food.response;

import java.util.List;

public record GetFoodsAutoResponse(List<String> foodJson) {
    public static GetFoodsAutoResponse from(List<String> foodJson) {
        return new GetFoodsAutoResponse(foodJson);
    }
}
