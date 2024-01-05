package com.gaebaljip.exceed.dto.response;

import com.gaebaljip.exceed.food.adapter.out.FoodEntity;

import java.util.List;

public record PageableFood(
        List<FoodEntity> foodEntities,
        Boolean hasNext,
        int size
) {
}
