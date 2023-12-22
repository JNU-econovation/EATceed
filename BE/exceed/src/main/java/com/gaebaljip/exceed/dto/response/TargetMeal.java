package com.gaebaljip.exceed.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gaebaljip.exceed.common.CustomDoubleSerializer;
import lombok.Builder;

public record TargetMeal(
        @JsonSerialize(using = CustomDoubleSerializer.class)
        Double targetCalorie,
        @JsonSerialize(using = CustomDoubleSerializer.class)
        Double targetCarbohydrate,
        @JsonSerialize(using = CustomDoubleSerializer.class)
        Double targetProtein,
        @JsonSerialize(using = CustomDoubleSerializer.class)
        Double targetFat
) {
        @Builder
        public TargetMeal {
        }
}
