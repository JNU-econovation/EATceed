package com.gaebaljip.exceed.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gaebaljip.exceed.common.CustomDoubleSerializer;
import lombok.Builder;

public record MaintainMeal(
        @JsonSerialize(using = CustomDoubleSerializer.class)
        Double maintainCalorie,
        @JsonSerialize(using = CustomDoubleSerializer.class)
        Double maintainCarbohydrate,
        @JsonSerialize(using = CustomDoubleSerializer.class)
        Double maintainProtein,
        @JsonSerialize(using = CustomDoubleSerializer.class)
        Double maintainFat) {

    @Builder
    public MaintainMeal {
    }
}
