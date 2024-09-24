package com.gaebaljip.exceed.application.port.in.member;

import lombok.Builder;

@Builder
public record UpdateWeightCommand(Double weight, Double targetWeight, Long memberId, String uri) {
    public static UpdateWeightCommand of(
            Double weight, Double targetWeight, Long memberId, String uri) {
        return UpdateWeightCommand.builder()
                .memberId(memberId)
                .weight(weight)
                .targetWeight(targetWeight)
                .uri(uri)
                .build();
    }
}
