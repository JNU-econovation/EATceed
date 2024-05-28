package com.gaebaljip.exceed.dto;

import lombok.Builder;

@Builder
public record UpdateWeightResponse(Double weight, Double targetWeight) {

    public static UpdateWeightResponse of(Double weight, Double targetWeight) {
        return UpdateWeightResponse.builder().weight(weight).targetWeight(targetWeight).build();
    }
}
