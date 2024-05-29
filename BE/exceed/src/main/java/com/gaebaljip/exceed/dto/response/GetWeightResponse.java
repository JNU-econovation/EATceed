package com.gaebaljip.exceed.dto.response;

public record GetWeightResponse(Double weight, Double targetWeight) {

    public static GetWeightResponse of(Double weight, Double targetWeight) {
        return new GetWeightResponse(weight, targetWeight);
    }
}
