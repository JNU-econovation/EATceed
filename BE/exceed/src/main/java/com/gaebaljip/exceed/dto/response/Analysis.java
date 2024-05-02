package com.gaebaljip.exceed.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gaebaljip.exceed.common.CustomDoubleSerializer;

import lombok.Builder;

public record Analysis(
        Boolean isVisited,
        LocalDateTime date,
        @JsonSerialize(using = CustomDoubleSerializer.class) double calorieRate,
        boolean proteinAchieve,
        boolean fatAchieve,
        boolean carbohydrateAchieve) {

    @Builder
    public Analysis {}
}
