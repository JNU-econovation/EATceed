package com.gaebaljip.exceed.dto;

import java.util.List;

import lombok.Builder;

public record SpecificMealDTO(CurrentMealDTO currentMealDTO, List<MealRecordDTO> mealRecordDTOS) {

    @Builder
    public SpecificMealDTO {}
}
