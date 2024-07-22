package com.gaebaljip.exceed.common.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record SpecificMealDTO(CurrentMealDTO currentMealDTO, List<MealRecordDTO> mealRecordDTOS) {

    public static SpecificMealDTO of(
            CurrentMealDTO currentMealDTO, List<MealRecordDTO> mealRecordDTOS) {
        return SpecificMealDTO.builder()
                .currentMealDTO(currentMealDTO)
                .mealRecordDTOS(mealRecordDTOS)
                .build();
    }
}
