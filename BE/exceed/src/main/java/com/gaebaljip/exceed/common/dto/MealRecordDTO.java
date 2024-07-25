package com.gaebaljip.exceed.common.dto;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gaebaljip.exceed.application.domain.meal.MealType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public record MealRecordDTO(
        @Schema(description = "식사 시간", pattern = "HH:mm:ss", example = "10:11")
                @JsonFormat(
                        shape = JsonFormat.Shape.STRING,
                        pattern = "HH:mm",
                        timezone = "Asia/Seoul")
                LocalTime time,
        MealType mealType,
        String imageUri,
        List<FoodDTO> foodDTOS) {

    @Builder
    public MealRecordDTO {}
}
