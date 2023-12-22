package com.gaebaljip.exceed.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gaebaljip.exceed.common.CustomDoubleSerializer;
import lombok.Builder;

public record CurrentMeal(
        @JsonSerialize(using = CustomDoubleSerializer.class)
        Double currentCalorie,
        @JsonSerialize(using = CustomDoubleSerializer.class)
        Double currentCarbohydrate,
        @JsonSerialize(using = CustomDoubleSerializer.class)
        Double currentProtein,
        @JsonSerialize(using = CustomDoubleSerializer.class)
        Double currentFat) {

    @Builder
    public CurrentMeal {
    }

}
