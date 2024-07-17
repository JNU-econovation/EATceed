package com.gaebaljip.exceed.common.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gaebaljip.exceed.common.CustomDoubleSerializer;

import lombok.Builder;

public record TargetMealDTO(
        @JsonSerialize(using = CustomDoubleSerializer.class) Double calorie,
        @JsonSerialize(using = CustomDoubleSerializer.class) Double carbohydrate,
        @JsonSerialize(using = CustomDoubleSerializer.class) Double protein,
        @JsonSerialize(using = CustomDoubleSerializer.class) Double fat) {
    @Builder
    public TargetMealDTO {}
}
