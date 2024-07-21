package com.gaebaljip.exceed.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gaebaljip.exceed.common.CustomDoubleSerializer;

import lombok.Builder;

@Builder
public record CurrentMealDTO(
        @JsonSerialize(using = CustomDoubleSerializer.class) Double calorie,
        @JsonSerialize(using = CustomDoubleSerializer.class) Double carbohydrate,
        @JsonSerialize(using = CustomDoubleSerializer.class) Double protein,
        @JsonSerialize(using = CustomDoubleSerializer.class) Double fat) {

    public static CurrentMealDTO of(
            Double calorie, Double carbohydrate, Double protein, Double fat) {
        return CurrentMealDTO.builder()
                .calorie(calorie)
                .carbohydrate(carbohydrate)
                .protein(protein)
                .fat(fat)
                .build();
    }
}
