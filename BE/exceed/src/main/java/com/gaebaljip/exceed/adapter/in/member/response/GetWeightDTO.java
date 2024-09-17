package com.gaebaljip.exceed.adapter.in.member.response;

public record GetWeightDTO(Double weight, Double targetWeight) {

    public static GetWeightDTO of(Double weight, Double targetWeight) {
        return new GetWeightDTO(weight, targetWeight);
    }
}
