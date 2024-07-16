package com.gaebaljip.exceed.dto.request;

import lombok.Builder;

public record UpdateWeightRequest(Double weight, Double targetWeight) {
    @Builder
    public UpdateWeightRequest {}
}
