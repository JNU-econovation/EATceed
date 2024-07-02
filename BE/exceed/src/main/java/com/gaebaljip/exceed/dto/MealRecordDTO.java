package com.gaebaljip.exceed.dto;

import java.time.LocalTime;
import java.util.List;

import com.gaebaljip.exceed.dto.FoodDTO;
import com.gaebaljip.exceed.meal.domain.MealType;

import lombok.Builder;

public record MealRecordDTO(
        LocalTime time, MealType mealType, String imageUri, List<FoodDTO> foodDTOS) {

    @Builder
    public MealRecordDTO {}
}
