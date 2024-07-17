package com.gaebaljip.exceed.common.dto;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gaebaljip.exceed.application.domain.meal.MealType;

import lombok.Builder;

public record MealRecordDTO(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
                LocalTime time,
        MealType mealType,
        String imageUri,
        List<FoodDTO> foodDTOS) {

    @Builder
    public MealRecordDTO {}
}
