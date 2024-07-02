package com.gaebaljip.exceed.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gaebaljip.exceed.common.CustomDoubleSerializer;

import lombok.Builder;

public record CurrentMeal(
        @JsonSerialize(using = CustomDoubleSerializer.class) Double calorie,
        @JsonSerialize(using = CustomDoubleSerializer.class) Double carbohydrate,
        @JsonSerialize(using = CustomDoubleSerializer.class) Double protein,
        @JsonSerialize(using = CustomDoubleSerializer.class) Double fat) {

    @Builder
    public CurrentMeal {}
}
