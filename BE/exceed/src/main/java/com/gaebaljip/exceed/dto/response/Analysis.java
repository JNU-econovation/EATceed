package com.gaebaljip.exceed.dto.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.gaebaljip.exceed.common.CustomDoubleSerializer;
import lombok.Builder;

import java.time.LocalDate;

public record Analysis(
        Boolean isVisited,
        LocalDate date,
        @JsonSerialize(using = CustomDoubleSerializer.class)
        double calorieRate,
        boolean proteinAchieve,
        boolean fatAchieve,
        boolean carbohydrateAchieve) {

    @Builder
    public Analysis {
    }
}
